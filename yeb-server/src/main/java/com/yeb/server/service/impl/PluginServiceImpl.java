package com.yeb.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.server.mapper.PluginMapper;
import com.yeb.server.pojo.Plugin;
import com.yeb.server.service.IPluginService;

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
public class PluginServiceImpl extends ServiceImpl<PluginMapper, Plugin> implements IPluginService {

}
