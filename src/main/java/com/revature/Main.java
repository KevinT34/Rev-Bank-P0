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
                    userController.promptUserForAccountService(controlMap);

                }

            }

        }

    }
}

/*
    TODO: Could grab all checking account info when logging in, or wait until user specifies actions.
          Will need to constantly update the in-memory Map and Database version
          of checking accounts whenever adding/closing/etc. Could grab all accounts when accessing

    TODO: Could make a Checking Controller



 */