package com.ssm.crud.test;

import com.github.pagehelper.PageInfo;
import com.ssm.crud.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 测试spring测试模块提供的请求功能，测试curd请求的准确性
 * spring4测试的手需要3.0的版本
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:springMVC.xml"})//,"file:src/main/webapp/WEB-INF/"
public class MvcTest {
    //传入springmvc的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟mvc请求，获取处理请求
    MockMvc mockMvc;

    @Before
    public void initMokcMvc(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/employee/findAll").param("pn","5")).andReturn();
        MockHttpServletRequest request=result.getRequest();
        PageInfo pi=(PageInfo)request.getAttribute("pageInfo");
        System.out.println("当前页码："+pi.getPageNum());
        System.out.println("总页码："+pi.getPages());
        System.out.println("总记录数"+pi.getTotal());
        System.out.println("在页面中需要连续显示的页码：");
        int[] nums=pi.getNavigatepageNums();
        for(int i:nums){
            System.out.println(" "+i);
        }
        //获取员工数据
        List<Employee> list=pi.getList();
        for(Employee employee:list){
            System.out.println("Id:"+employee.getEmpId()+" name:"+employee.getEmpName());
        }
    }
}
