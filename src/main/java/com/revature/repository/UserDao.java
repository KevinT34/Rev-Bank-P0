package com.revature.repository;

import com.revature.entity.User;

import java.util.List;

/*
    Use dao to define what we want User data persistence to look like, then we implement in classes
 */
public interface UserDao {

    //same as: public abstract User createUser(...)
    User createUser(User newUserCredentials);

    List<User> getAllUsers();
}
