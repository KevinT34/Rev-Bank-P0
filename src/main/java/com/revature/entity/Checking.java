package com.revature.entity;

import java.util.Objects;

public class Checking {

    int number;
    double sum;
    int userId;

    public Checking(){}

    public Checking(int num, double sum, int userId) {
        this.number = num;
        this.sum = sum;
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checking checking = (Checking) o;
        return number == checking.number && Double.compare(sum, checking.sum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, sum);
    }

    @Override
    public String toString() {
        return "Checking{" +
                "number=" + number +
                ", totalSum=" + sum +
                '}';
    }
}
