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
            if (Objects.equals(sourceId, destinationId)) {
                throw new IllegalArgumentException("Accounts id can't be the same");
            }
            Account accountFrom = getAccountById(sourceId);
            if (accountFrom.getBalance() < amount) {
                throw new IllegalArgumentException("Amount can't be greater than account balance");
            }
            Account accountTo = getAccountById(destinationId);
            withdraw(accountFrom, amount);
            deposit(accountTo, amount);
        } finally {
            lock.unlock();
        }
    }

    public Account createNewAccount(long initialBalance) {
        Account account = new Account(UUID.randomUUID().toString(), initialBalance);
        try {
            lock.lock();
            accounts.put(account.getId(), account);
            return account;
        } finally {
            lock.unlock();
        }
    }

    public void deleteAccountById(String accountId) {
        try {
            lock.lock();
            if (accountId != null || accounts.containsKey(accountId)) {
                throw new IllegalArgumentException("Account not found.");
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public Account getAccountById(String id) {
        try {
            lock.lock();
            if (!accounts.containsKey(id)) {
                throw new IllegalArgumentException(String.format("Account with %s not found.", id));
            }
            return accounts.get(id);
        } finally {
            lock.unlock();
        }
    }

    private void withdraw(Account account, long amount) {
        account.setBalance(account.getBalance() - amount);
    }

    private void deposit(Account account, long amount) {
        account.setBalance(account.getBalance() + amount);
    }

}
