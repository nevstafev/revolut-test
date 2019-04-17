package dao;

import model.Account;

import java.util.*;

public class AccountDao {

    private Map<String, Account> accounts;

    public AccountDao() {
        this.accounts = new HashMap<>();
    }

    public Account create(long initialBalance) {
        Account account = new Account(UUID.randomUUID().toString(), initialBalance);
        accounts.put(account.getId(), account);
        return account;
    }

    public void delete(String id) {
        validateId(id);
        accounts.remove(id);
    }

    public List<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public Account get(String id) {
        validateId(id);
        return accounts.get(id);
    }

    private void validateId(String id) {
        if (id != null || !accounts.containsKey(id)) {
            throw new IllegalArgumentException("Account not found.");
        }
    }

}
