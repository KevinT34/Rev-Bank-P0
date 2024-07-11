package com.revature.entity;

import java.util.Objects;

public class Checking {

    int number;
    double totalSum;

    public Checking(){}

    public Checking(int num, double totalSum) {
        this.number = num;
        this.totalSum = totalSum;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checking checking = (Checking) o;
        return number == checking.number && Double.compare(totalSum, checking.totalSum) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, totalSum);
    }

    @Override
    public String toString() {
        return "Checking{" +
                "number=" + number +
                ", totalSum=" + totalSum +
                '}';
    }
}
