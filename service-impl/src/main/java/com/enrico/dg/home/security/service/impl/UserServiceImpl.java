package com.enrico.dg.home.security.service.impl;

import com.enrico.dg.home.security.dao.api.UserRepository;
import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.entity.dao.common.User;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import com.enrico.dg.home.security.libraries.utility.PasswordHelper;
import com.enrico.dg.home.security.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findOne(String id) {

    User user = userRepository.findByIsDeletedAndId(0, id);

    if (user == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return user;
  }

  @Override
  public List<User> findAll() {

    List<User> userList = userRepository.findByIsDeleted(0);

    if(userList == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return userList;
  }

  @Override
  public User register(User user) {

    User newUser = new User();

    if(PasswordHelper.isPasswordValid(user.getPassword())) {
      newUser.setPassword(PasswordHelper.encryptPassword(user.getPassword()));
    }

    newUser.setName(user.getName());
    newUser.setEmail(user.getEmail());
    newUser.setRole(user.getRole());
    newUser.setMacAddress(user.getMacAddress());
    newUser.setSosNumber(user.getSosNumber());
    newUser.setEmergencyNumber(user.getEmergencyNumber());

    try{
      return userRepository.save(newUser);
    } catch (Exception e) {
      throw new BusinessLogicException(ResponseCode.DUPLICATE_DATA.getCode(),
              ResponseCode.DUPLICATE_DATA.getMessage());
    }
  }

  @Override
  public User login(String email) {

    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    return user;
  }

  @Override
  public User update(String id, User user) {

    User newUser = userRepository.findByIsDeletedAndId(0, id);

    if (newUser == null) {
      throw new BusinessLogicException(ResponseCode.DATA_NOT_EXIST.getCode(),
              ResponseCode.DATA_NOT_EXIST.getMessage());
    }

    newUser.setMacAddress(user.getMacAddress());
    newUser.setSosNumber(user.getSosNumber());
    newUser.setEmergencyNumber(user.getEmergencyNumber());

    return userRepository.save(newUser);
  }
}
