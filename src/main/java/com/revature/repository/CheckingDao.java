package com.revature.repository;

import com.revature.entity.Checking;

import java.util.Map;

public interface CheckingDao {

    Checking createChecking(int userId, Checking newCheckingAccount);

    Map<Integer, Checking> getAllAccounts(int userId);
}
