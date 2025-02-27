package com.revature.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
    This class is designed as a Java Bean: this is a design pattern that follows standard
    naming and development practices:
        - the class implements Serializable, which allows it to be turned into a byte stream
        by Java
        - all fields of the class are private and have public getters/setters
        - a no args constructor is present
        - equals and hashcode are overridden
 */

public class User implements Serializable {



    private int userId;

    private String username;

    private String password;

    //when adding accounts, pull-up map.size() and ++ for account name
    private Map<Integer, Checking> accounts = new HashMap<>();

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<Integer, Checking> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<Integer, Checking> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(accounts, user.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, accounts);
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", number of accounts='" + accounts.size() + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "{User: " + username +
                ", Password: " + password +
                ", Number of Accounts: " + accounts.size() + "}";

    }

}
