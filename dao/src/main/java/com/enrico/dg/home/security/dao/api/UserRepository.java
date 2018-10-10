package com.enrico.dg.home.security.dao.api;

import com.enrico.dg.home.security.entity.dao.common.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
    User findByIsDeletedAndId(Integer isDeleted, String id);
    List<User> findByIsDeleted(Integer isDeleted);
}