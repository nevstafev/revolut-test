package service;

import model.Account;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountService {

    private Map<String, Account> accounts;
    private Lock lock = new ReentrantLock();

    public AccountService() {
        this.accounts = new HashMap<>();
    }

    public void transferMoney(String sourceId, String destinationId, long amount) {
        try {
            lock.lock();
            if(Objects.equals(sourceId, destinationId)) {
                throw new IllegalArgumentException("Accounts id can't be the same");
            }
            Account accountFrom = getAccountById(sourceId);
            Account accountTo = getAccountById(destinationId);


        } finally {
            lock.unlock();
        }
    }


    private Account getAccountById(String id) {
        try {
            lock.lock();
            if(!accounts.containsKey(id)) {
                throw new IllegalArgumentException(String.format("Account with %s not found.", id));
            }
            return accounts.get(id);
        } finally {
            lock.unlock();
        }
    }

}
