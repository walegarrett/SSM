package com.ssm.crud.service.impl;

import com.ssm.crud.bean.Employee;
import com.ssm.crud.bean.EmployeeExample;
import com.ssm.crud.dao.EmployeeMapper;
import com.ssm.crud.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Override
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    /**
     * 检验用户名是否有用
     * @param empName
     * @return true当前姓名可用
     */
    @Override
    public boolean checkUser(String empName) {
        EmployeeExample example=new EmployeeExample();

        EmployeeExample.Criteria criteria=example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count=employeeMapper.countByExample(example);
        return count==0;
    }

    /**
     * 获取员工信息
     * @param id
     * @return
     */
    @Override
    public Employee getEmp(Integer id) {
        Employee employee=employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

    /**
     * 更新员工信息
     * @param employee
     */
    @Override
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);//有选择的更新，带了哪个就更新哪个
    }

    /*
    根据员工id删除记录
     */
    @Override
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example=new EmployeeExample();
        EmployeeExample.Criteria criteria=example.createCriteria();
        //delete form  xxx where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }
}
