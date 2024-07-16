package com.revature.controller;

import com.revature.entity.Checking;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.exception.RegistrationError;
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

    public boolean promptUserForService(Map<String, User> controlMap) {
        //prompt user to make an account
        System.out.println("What would you like to do?");
        System.out.println("1. Register an account");
        System.out.println("2. Login");
        System.out.println("q. Quit");
        try {
            String userActionIndicated = scanner.nextLine();
            switch (userActionIndicated) {
                case "1":
                    registerNewUser();
                    break;
                case "2":
                    User currentUser = login();
                    controlMap.put("User", currentUser);
                    loadAccounts(currentUser);
                    break;
                case "q":
                    System.out.println("Goodbye!");
                    return false;
            }
        } catch(LoginFail | RegistrationError exc) {
            System.out.println(exc.getMessage());
        }
        return true;
    }

    public void promptUserForAccountService(Map<String, User> controlMap){
        System.out.println("What would you like to do?");
        System.out.println("1. View accounts"); //specify account after and see if you want to increase/decrease funds
        System.out.println("2. Make Deposit"); //deposit into account
        System.out.println("3. Make Withdrawal"); //withdraw from account
        System.out.println("4. Open new Checking Account"); //going to need to pull the latest one into the map
        System.out.println("5. Close a Checking Account"); //pop from map in memory
        System.out.println("6. Logout"); //remove user and accounts

        String userActionIndicated = scanner.nextLine();

        switch (userActionIndicated) {
            case "1":
                viewAccounts(controlMap.get("User"));
                break;
            case "2":
                addMoney(controlMap.get("User"));
                break;
            case "3":
                removeMoney(controlMap.get("User"));
                break;
            case "4":
                System.out.println("New Account added!");
                createCheckingAccount(controlMap.get("User"));
                break;
            case "5":
                deleteCheckingAccount(controlMap.get("User"));
                break;
            case "6":
                System.out.println("- Logging out -");
                controlMap.remove("User");
        }
    }

//    public void returnResultOfRegistrationAttempt(User user) {
//        System.out.printf("New account creation: %s", user);
//    }

    public void registerNewUser() throws RegistrationError {
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

    public void loadAccounts(User currentUser){
        //load all accounts into user
        userService.loadUserAccounts(currentUser);

    }

    public void createCheckingAccount(User currentUser) {

        userService.createCheckingAccount(currentUser); //adding account in memory and db

    }

    public void deleteCheckingAccount(User currentUser) {
        //check if any accounts, if not, error out and say no accounts
        //ask which account to delete

        if(viewAccounts(currentUser)) {
            int userResponse;
            System.out.println("Enter an account number to delete or hit 0 to return: ");
            userResponse = Integer.parseInt(scanner.nextLine());
            //need to make sure userinput is a number
            if (userResponse != 0) {
                userService.deleteCheckingAccount(userResponse, currentUser);

            }

            //validate response in service layer if it is valid number for accounts
            //call delete and pop it from user map
            //call delete and remove it from DB
            //display removed account to user

        }

    }

    public boolean viewAccounts(User currentUser) {
        if(currentUser.getAccounts().isEmpty()) {
            System.out.println("No accounts found for " + currentUser.getUsername());
            return false;
        }
        Map<Integer, Checking> userAccounts = currentUser.getAccounts();
        for (Map.Entry<Integer, Checking> checkingAccount : userAccounts.entrySet()) {
            System.out.printf("Account Number: %s, Balance: $%.2f \n", checkingAccount.getKey(), checkingAccount.getValue().getSum());

        }
        return true;
    }

    public void addMoney(User currentUser) {
        //check if there is an account
        //call service
        //make sure valid amount added (non negative)
        //save in mem and db
        if(viewAccounts(currentUser)) {
            int userResponse;
            System.out.println("Enter an account number to deposit into or hit 0 to return: ");
            userResponse = Integer.parseInt(scanner.nextLine());
            //need to make sure user input is a number
            if (userResponse != 0) {
                //make sure account number is valid
                if (userService.accountIdValidator(userResponse, currentUser)) {
                    double depositAmnt;
                    System.out.println("Enter an amount to deposit: ");
                    depositAmnt = Double.parseDouble(scanner.nextLine());
                    userService.depositMoney(depositAmnt, userResponse, currentUser);
                } else {
                    System.out.println("Invalid account selected");
                }

            }
        }
    }

    public void removeMoney(User currentUser) {
        //check if there is an account
        //call service
        //make sure valid amount is added (non negative && balance > 0)
        //save in mem and db
        if(viewAccounts(currentUser)) {
            int userResponse;
            System.out.println("Enter an account number to withdraw from or hit 0 to return: ");
            userResponse = Integer.parseInt(scanner.nextLine());
            if (userResponse != 0) {
                //make sure account number is valid
                if (userService.accountIdValidator(userResponse, currentUser)) {
                    double withdrawAmnt;
                    System.out.println("Enter an amount to withdraw: ");
                    withdrawAmnt = Double.parseDouble(scanner.nextLine());
                    userService.withdrawMoney(withdrawAmnt, userResponse, currentUser);
                } else {
                    System.out.println("Invalid account selected");
                }

            }
        }

    }



}
