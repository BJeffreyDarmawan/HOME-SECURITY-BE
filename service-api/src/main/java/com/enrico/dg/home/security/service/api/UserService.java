package com.enrico.dg.home.security.service.api;

import com.enrico.dg.home.security.entity.dao.common.User;

import java.util.List;

public interface UserService {

  User findOne(String id);

  List<User> findAll();

  User register(User user);

  User login(String email);
}
