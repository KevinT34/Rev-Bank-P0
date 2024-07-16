package com.revature.service;

import com.revature.entity.Checking;
import com.revature.entity.User;
import com.revature.exception.LoginFail;
import com.revature.exception.RegistrationError;
import com.revature.repository.CheckingDao;
import com.revature.repository.UserDao;

import java.util.List;
import java.util.Map;

public class UserService {

    private UserDao userDao;
    private CheckingDao checkingDao;

    public UserService(UserDao userDao, CheckingDao checkingDao) {
        this.userDao = userDao;
        this.checkingDao = checkingDao;
    }

    //entrypoint into UserService registration functionality
    public User validateNewCredentials(User newUserCredentials) throws RegistrationError{
        //1. Check if lengths are correct
        if(checkUsernamePasswordLength(newUserCredentials)) {
            //2. Check if username is unique
            if(checkUsernameUnique(newUserCredentials)) {
                //3. Persist user data if valid, reject if not
                return userDao.createUser(newUserCredentials);
            }
        }
        //4. Inform user of results
        throw new RegistrationError("Invalid Username or Password");
    }

    public User checkLoginCredentials(User credentials) {
        for (User user : userDao.getAllUsers()) {
            boolean usernameMatches = user.getUsername().equals(credentials.getUsername());
            boolean passwordMatches = user.getPassword().equals(credentials.getPassword());
            if (usernameMatches && passwordMatches) {
                credentials.setUserId(user.getUserId());
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

    public void loadUserAccounts(User currentUser) {
        Map<Integer, Checking> loadedAccounts = checkingDao.getAllAccounts(currentUser.getUserId());
        currentUser.setAccounts(loadedAccounts);
    }

    public void createCheckingAccount(User currentUser){

        //Create checking account without id
        //add checking account to DAO and receive account #
        //update checking account in memory of User
        Checking newAccount = new Checking(0, currentUser.getUserId());

        //Add new account in DB
        newAccount = checkingDao.createChecking(newAccount);

        //Add new account in memory from DB
        Map<Integer, Checking> currentUserAccounts = currentUser.getAccounts();
        currentUserAccounts.put(newAccount.getNumber(), newAccount);



        //return currentUser;
    }

    public void deleteCheckingAccount(int userInput, User currentUser) {

        if(accountIdValidator(userInput, currentUser)) {
            //delete in memory
            Checking deletedAcc = currentUser.getAccounts().get(userInput);
            currentUser.getAccounts().remove(userInput);
            //delete in DB
            checkingDao.deleteAccountByAccountId(userInput, currentUser.getUserId());
            System.out.println(deletedAcc.toString() + " deleted");
        } else {
            System.out.println("Invalid account selected");
        }

    }

    public boolean accountIdValidator(int userInput, User currentUser) {
        Map<Integer, Checking> accounts = currentUser.getAccounts();
        //make sure account number in the map
        for(Integer accountIds : accounts.keySet()) {
            if (accountIds == userInput) {
                return true;
            }
        }
        return false;
    }

    public void depositMoney(double depositAmnt, int selectedAcc, User currentUser) {
        if(depositAmnt > 0) {
            //deposit in memory
            Checking currentAccount = currentUser.getAccounts().get(selectedAcc);
            double newSum = currentAccount.getSum() + depositAmnt;
            currentAccount.setSum(newSum);
            currentUser.getAccounts().put(currentAccount.getNumber(), currentAccount);

            //deposit in DB
            checkingDao.modifyAccount(selectedAcc, newSum, currentUser.getUserId());
            System.out.printf("$%.2f deposited into account number %s\n", depositAmnt, selectedAcc);
            System.out.printf("New account balance: $%.2f\n", newSum);

        } else {
            System.out.println("Cannot deposit a negative amount");
        }
    }

    public void withdrawMoney(double withdrawAmnt, int selectedAcc, User currentUser) {
        Checking currentAccount = currentUser.getAccounts().get(selectedAcc);
        if(withdrawAmnt < 0) {
            System.out.println("Invalid withdrawal amount, cannot withdraw a negative amount");
        } else if ((currentAccount.getSum() - withdrawAmnt) < 0){
            System.out.println("Invalid withdrawal amount, withdrawal must not result in a negative account balance");
        } else {
            //withdraw in memory
            double newSum = currentAccount.getSum() - withdrawAmnt;
            currentAccount.setSum(newSum);
            currentUser.getAccounts().put(currentAccount.getNumber(), currentAccount);

            //withdraw in DB
            checkingDao.modifyAccount(selectedAcc, newSum, currentUser.getUserId());
            System.out.printf("$%.2f withdrawn from account number %s\n", withdrawAmnt, selectedAcc);
            System.out.printf("New account balance: $%.2f\n", newSum);
        }

    }



}
