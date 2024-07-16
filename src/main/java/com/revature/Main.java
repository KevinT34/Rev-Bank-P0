package com.revature;

import com.revature.controller.UserController;
import com.revature.entity.*;
import com.revature.repository.*;
import com.revature.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //User user = new User("admin", "123");
        //System.out.println(user.getUsername());


        try(Scanner scanner = new Scanner(System.in)) {
            UserDao userDao = new SqliteUserDao();
            CheckingDao checkingDao = new SqliteCheckingDao();
            UserService userService = new UserService(userDao, checkingDao);
            UserController userController = new UserController(scanner, userService);

            boolean loopApplication = true;
            //this map will update the loop application boolean and store the logged-in user data
            Map<String, User> controlMap = new HashMap<>();

//            Map<String, String> controlMap = new HashMap<>();
//            controlMap.put("Continue Loop", "true");
//            while (Boolean.parseBoolean(controlMap.get("Continue Loop"))) {
//                userController.promptUserForService(controlMap);
//                if(controlMap.containsKey("User")) {
//                    System.out.printf("Banking stuff for %s can happen here! press any key to continue", controlMap.get("User"));
//                    scanner.nextLine();
//                    userController.promptUserForAccountService(controlMap);
//
//                }
//
//            }

            while (loopApplication) {
                loopApplication = userController.promptUserForService(controlMap);
                while(controlMap.containsKey("User")) {
                    System.out.printf("Banking stuff for %s can happen here! Press any key to continue", controlMap.get("User"));
                    scanner.nextLine();
                    userController.promptUserForAccountService(controlMap);

                }


            }

        }

    }
}

/*

    TODO: New exception for user input when it is a string when selecting in menu for:
            delete, deposit, withdraw, as well as when inputting amounts to modify
    TODO: double check any todos




 */