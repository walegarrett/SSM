package com.ssm.crud.service;

import com.ssm.crud.bean.Employee;

import java.util.List;

public interface IEmployeeService {
    public List<Employee> getAll();
/*
员工保存方法
 */
    void saveEmp(Employee employee);

    public boolean checkUser(String empName);

    public Employee getEmp(Integer id);

    public void updateEmp(Employee employee);

    public void deleteEmp(Integer id);

    public void deleteBatch(List<Integer> ids);
}
