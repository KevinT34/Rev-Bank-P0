package com.revature.entity;

import java.io.Serializable;
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

    private String username;

    private String password;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
