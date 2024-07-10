package com.revature;

import com.revature.controller.UserController;
import com.revature.entity.*;
import com.revature.repository.InMemoryUser;
import com.revature.repository.SqliteUserDao;
import com.revature.repository.UserDao;
import com.revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        User user = new User("admin", "123");


        System.out.println(user.getUsername());

        /*
            Registration Steps:
                - user needs to prompt they want to make an acc
                - user needs to provide a username and password
                - system needs to check the username and password conform to sw req
                - inform the user of results
         */

        try(Scanner scanner = new Scanner(System.in)) {
            UserDao userDao = new SqliteUserDao();
            UserService userService = new UserService(userDao);
            UserController userController = new UserController(scanner, userService);

            boolean loopApplication = true;
            //this map will update the loop application boolean and store the logged-in user data
            Map<String, String> controlMap = new HashMap<>();
            controlMap.put("Continue Loop", "true");
            while (Boolean.parseBoolean(controlMap.get("Continue Loop"))) {
                userController.promptUserForService(controlMap);
                if(controlMap.containsKey("User")) {
                    System.out.printf("Banking stuff for %s can happen here! press any key to continue", controlMap.get("User"));
                    scanner.nextLine();
                    /*
                        NOTE: currently the user information has no means of being removed: when you implement a logout
                        functionality, the controlMap needs to have the User key/value pair removed - controlMap.remove("User");
                     */
                }
            }

        }

    }
}