package home.utils;

import home.model.Account;

import java.util.Objects;

public class TransferUtils {
    public void transferMoney(Account source, Account destination, long amount) {
        if (Objects.equals(source.getId(), destination.getId())) {
            throw new IllegalArgumentException("AccountDao id can't be the same.");
        }
        if (source.getBalance() < amount) {
            throw new IllegalArgumentException("Amount can't be greater than account balance.");
        }
        withdraw(source, amount);
        deposit(destination, amount);
    }

    private void withdraw(Account account, long amount) {
        account.setBalance(account.getBalance() - amount);
    }

    private void deposit(Account account, long amount) {
        account.setBalance(account.getBalance() + amount);
    }

}
