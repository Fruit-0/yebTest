package com.yeb.service.impl;

import com.yeb.pojo.Admin;
import com.yeb.mapper.AdminMapper;
import com.yeb.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
