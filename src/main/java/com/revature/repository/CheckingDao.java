package com.revature.repository;

import com.revature.entity.Checking;

import java.util.Map;

public interface CheckingDao {

    Checking createChecking(Checking newCheckingAccount);

    Map<Integer, Checking> getAllAccounts(int userId);

    void deleteAccountByAccountId(int accountId, int userId);

    void modifyAccount(int accountId, double deposit, int userId);

    Checking getAccountByAccountId(int accountId, int userId);
}
