package com.eim.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eim.entity.User;
import com.eim.mapper.UserMapper;
import com.eim.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
