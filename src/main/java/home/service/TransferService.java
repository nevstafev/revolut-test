package home.service;

import home.utils.TransferUtils;
import home.dao.AccountDao;
import home.dao.TransactionDao;
import home.model.Account;
import home.model.Transaction;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TransferService {

    AccountDao accounts;
    TransactionDao transactions;
    Executor executor = Executors.newSingleThreadExecutor();
    ReentrantLock lock = new ReentrantLock();
    TransferUtils utils = new TransferUtils();

    public TransferService(AccountDao accounts, TransactionDao transactions) {
        this.accounts = accounts;
        this.transactions = transactions;
    }

    public List<Account> getAccounts() {
        return accounts.getAll();
    }

    public Account getAccountById(String id) {
        return accounts.get(id);
    }

    public Account createAccount(long initialBalance) {
        try {
            lock.lock();
            return accounts.create(initialBalance);
        } finally {
            lock.unlock();
        }
    }

    public void deleteAccount(String id) {
        try {
            lock.lock();
            accounts.delete(id);
        } finally {
            lock.unlock();
        }
    }

    public Transaction getTransaction(String transactionId) {
        return transactions.get(transactionId);
    }

    public List<Transaction> getTransactions() {
        return transactions.getAll();
    }

    public Transaction createTransferRequest(String sourceAccountId, String destinationAccountId, long amount) {
        Transaction transaction = transactions.create(sourceAccountId, destinationAccountId, amount);
        Runnable task = createTransactionTask(transaction);
        executor.execute(task);
        transaction.setStatus(Transaction.SUBMITTED);
        return transaction;
    }

    private Runnable createTransactionTask(Transaction transaction) {
        return () -> {
                try {
                    lock.lock();
                    transaction.setStatus(Transaction.RUNNING);
                    Account source = accounts.get(transaction.getSourceId());
                    Account destination = accounts.get(transaction.getDestinationId());
                    utils.transferMoney(source, destination, transaction.getAmount());
                    transaction.setStatus(Transaction.FINISHED);
                } catch (Exception e) {
                    transaction.setStatus(Transaction.FAILED);
                } finally {
                    lock.unlock();
                }
            };
    }
}
