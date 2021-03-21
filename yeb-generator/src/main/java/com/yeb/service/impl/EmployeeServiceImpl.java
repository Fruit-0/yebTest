package com.yeb.service.impl;

import com.yeb.pojo.Employee;
import com.yeb.mapper.EmployeeMapper;
import com.yeb.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fruit
 * @since 2021-03-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
