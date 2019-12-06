package com.ssm.crud.service.impl;

import com.ssm.crud.bean.Department;
import com.ssm.crud.bean.DepartmentExample;
import com.ssm.crud.dao.DepartmentMapper;
import com.ssm.crud.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("departmentService")
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> findAll() {
        List list=departmentMapper.selectByExample(null) ;//查询所有结果
        return list;
    }
}
