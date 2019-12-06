package com.ssm.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.crud.bean.Employee;
import com.ssm.crud.bean.Msg;
import com.ssm.crud.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理员工crud请求
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    IEmployeeService iEmployeeService;

    /**
     * 批量删除多个记录以及一个记录，合二为一
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids")String ids ){
        if(ids.contains("-")){
            String[] str_ids=ids.split("-");
            //组装ids的数组
            List<Integer> del_ids=new ArrayList<>();
            for(String string:str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            iEmployeeService.deleteBatch(del_ids);
//            for(String id:str_ids){
//                Integer id=Integer.parseInt(ids);
//                iEmployeeService.deleteEmp(id);
//            }
        }else{
            //删除单个记录
            Integer id=Integer.parseInt(ids);
            iEmployeeService.deleteEmp(id);
        }

        return Msg.success();
    }

    /**
     * 根据id删除单个记录
     * @param id
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value="/emp/{id}",method=RequestMethod.DELETE)
//    public Msg deleteEmpById(@PathVariable("id")Integer id){
//        iEmployeeService.deleteEmp(id);
//        return Msg.success();
//    }
    /**
     * 如果直接PUT的形式发送请求
     * 封装的数据只有路径上的参数，其他的全为null
     * 问题：请求体中有数据，但是employee对象封装不上
     * 原因：
     *        1.tomcat将请求的数据封装为一个map,request.getParameter("empName")将会从map中取值
     *        2. springMVC封装pojo对象的时候，会把pojo中的每个属性的值request.getParameter("empName")
     *        ajax发送put请求引发的血案：
     *              put请求，请求体的数据拿不到
     *              tomcat一看到put不会封装请求体中的数据封装为map,只有post形式的请求才封装为请求体为map
     * @param employee
     * @return
     */
    @RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)//这里的empId要和bean中的属性相同
    @ResponseBody
    public Msg saveEmp(Employee employee){
        //System.out.println("将要更新的员工数据为："+employee);
        iEmployeeService.updateEmp(employee);

        return Msg.success();
    }
    /**
     * 根据id获取员工的信息
     * @param id
     * @return
     */
    @RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id")Integer id){
        Employee employee=iEmployeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    /**
     * 检验用户名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkUser(@RequestParam("empName") String empName){
        //先判断用户名是否是合法的表达式
        String regx="(^[a-zA-Z0-9_]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }
        //数据库用户名重复校验
        boolean b=iEmployeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }

    /**
     * 返回json字符串
     * @return
     */
    @RequestMapping("/findAll")
    @ResponseBody//自动将返回的值包装为json数据
    public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
//引入分页插件,在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟着的就是一个分页查询
        List<Employee> list=iEmployeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就可以了
        PageInfo page=new PageInfo(list,5);
        return Msg.success().add("pageInfo",page);
    }
    /**
     * 保存前端提交的数据
     * @param
     * @return
     */
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody//这个一定要加否则报错404,返回json数据
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            //校验失败，返回失败
            Map<String,Object> map=new HashMap<>();
            List<FieldError>errors =result.getFieldErrors();
            for(FieldError fieldError:errors){
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else{
            iEmployeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * 这里是查询所有的结果返回一个list给界面
     * @param pn
     * @param model
     * @return
     */
    @RequestMapping("/findAll1")
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){

        //引入分页插件,在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟着的就是一个分页查询
        List<Employee> list=iEmployeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就可以了
        PageInfo page=new PageInfo(list,5);

        model.addAttribute("pageInfo",page);
        return "list";
    }
}
