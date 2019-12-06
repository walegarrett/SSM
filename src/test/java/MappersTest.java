import com.ssm.crud.bean.Employee;
import com.ssm.crud.dao.DepartmentMapper;
import com.ssm.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;


/**
 * 使用Spring的单元测试
 *  1. 导入spring-test.jar
 *  2. 使用注解@ContextConfiguration指定spring配置文件位置
 *  3. @RunWith指定单元测试模块
 *  4. 就可以ioc容器实现注入
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})

public class MappersTest {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    /**
     * 测试departmentMapper
     */
    @Test
    public void testCRUD(){
        /*
            常规原生的方法：
            //创建springIOC容器
            ApplicationContext ioc=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
            ioc.getBean("DepartmentMapper");//从容器中获取mapper
         */
        //1.插入几个部门
//        departmentMapper.insert(new Department(2,"开发部门"));
//        departmentMapper.insert(new Department(3,"测试部门"));

        //2.插入几个员工
        //employeeMapper.insert(new Employee(3,"jerry","M","Jerry@ss.com",2));
        //3.批量插入
//        for(){
//            employeeMapper.insert(new Employee(3,"jerry","M","Jerry@ss.com",2));
//        }

        EmployeeMapper mapper=sqlSession.getMapper(EmployeeMapper.class);
        for(int i=0;i<1000;i++){
            String name=UUID.randomUUID().toString().substring(0,5);
            mapper.insertSelective(new Employee(null,name,"M",name+"@ss.com",3));
        }
    }
    @Test
    public void demo1(){
        /*
            测试组件等是否有效
         */
        //System.out.println(departmentMapper);

        /**
         * 测试插入操作是否有效
         */
//        Department department=new Department();
//        department.setDeptId(1);
//        department.setDeptName("第一个");
//        departmentMapper.insert(department);

        /*
        测试查询方法是否有效
         */
        employeeMapper.insert(new Employee(2,"黄辉","M","2064@qq.com",3));
        Employee employee=employeeMapper.selectByPrimaryKey(2);//根据主键查找信息
        System.out.println(employee);

    }
}
