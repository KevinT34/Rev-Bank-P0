package com.revature.controller;

import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.service.UserService;

import java.util.Map;
import java.util.Scanner;

public class UserController {

    private Scanner scanner;

    private UserService userService;

    public UserController(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    public void promptUserForService(Map<String, String> controlMap) {
        //prompt user to make an account
        System.out.println("What would you like to do?");
        System.out.println("1. Register an account");
        System.out.println("2. Login");
        System.out.println("q. quit");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    controlMap.put("User", login().getUsername());
                    break;
                case "q":
                    System.out.println("Goodbye!");
                    controlMap.put("Continue Loop", "false");
                    controlMap.remove("User");
            }
        } catch(LoginFail exc) {
            System.out.println(exc.getMessage());
        }

    }

//    public void returnResultOfRegistrationAttempt(User user) {
//        System.out.printf("New account creation: %s", user);
//    }

    public void registerNewUser() {
        User newCredentials = getUserCredentials();
        User newUser = userService.validateNewCredentials(newCredentials);
        System.out.printf("New account creation: %s", newUser);
    }

    public User login(){

        return userService.checkLoginCredentials(getUserCredentials());

    }

    public User getUserCredentials(){
        String newUsername;
        String newPassword;
        //user needs to provide a username and password
        System.out.print("Please enter a username: ");
        newUsername = scanner.nextLine();
        System.out.print("Please enter a password: ");
        newPassword = scanner.nextLine();
        //System.out.println("Username: " + newUsername + " Password: " + newPassword);
        return new User(newUsername, newPassword);
    }
}
