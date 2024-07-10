package com.revature.service;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.repository.UserDao;

import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    //entrypoint into UserService registration functionality
    public User validateNewCredentials(User newUserCredentials){
        //1. Check if lengths are correct
        if(checkUsernamePasswordLength(newUserCredentials)) {
            //2. Check if username is unique
            if(checkUsernameUnique(newUserCredentials)) {
                //3. Persist user data if valid, reject if not
                return userDao.createUser(newUserCredentials);
            }
        }
        //4. Inform user of results
        throw new RuntimeException("placeholder for custom exception");
    }

    public User checkLoginCredentials(User credentials) {
        for (User user : userDao.getAllUsers()) {
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());
            if (usernameMatches && passwordMatches) {
                return credentials;
            }
        }
        throw new LoginFail("Credentials are invalid: please try again");
    }

    private boolean checkUsernamePasswordLength(User newUserCredentials) {
        boolean usernameIsValid = newUserCredentials.getUsername().length() <= 30;
        boolean passwordIsValid = newUserCredentials.getPassword().length() <= 30;
        return usernameIsValid && passwordIsValid;
    }

    private boolean checkUsernameUnique(User newUserCredentials){
        boolean usernameIsUnique = true;
        List<User> users = userDao.getAllUsers();

        for(User user : users) {
            if(newUserCredentials.getUsername().equals(user.getUsername())){
                usernameIsUnique = false;
                break;
            }
        }

        return usernameIsUnique;
    }

}
