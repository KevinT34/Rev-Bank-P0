package com.revature.repository;

import com.revature.entity.Checking;

import java.util.Map;

public class SqliteCheckingDao implements CheckingDao{

    @Override
    public Checking createChecking(int userId, Checking newCheckingAccount) {
        return null;
    }

    @Override
    public Map<Integer, Checking> getAllAccounts(int userId) {
        return Map.of();
    }

}
