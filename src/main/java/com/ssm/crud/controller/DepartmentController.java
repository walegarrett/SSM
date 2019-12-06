package com.ssm.crud.controller;

import com.ssm.crud.bean.Department;
import com.ssm.crud.bean.Msg;
import com.ssm.crud.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    IDepartmentService iDepartmentService;

    /**
     * 返回所有的部门信息
     * @param
     * @return
     */
    @RequestMapping("/depts")
    @ResponseBody//这个一定要加否则报错404
    public Msg getDepts(){
        List<Department> list=iDepartmentService.findAll();
        return Msg.success().add("depts",list);
    }

    /**
     * 返回所有的部门信息
     * @param model
     * @return
     */
    //查询所有记录
    @RequestMapping("/findAll")
    public String findAll(Model model){
        List list=iDepartmentService.findAll();
        model.addAttribute("list",list);
        return "list";
    }
}
