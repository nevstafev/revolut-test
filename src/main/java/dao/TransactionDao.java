package dao;

import model.Transaction;

import java.util.*;

public class TransactionDao {

    Map<String, Transaction> transactions = new HashMap<>();

    public Transaction create(String sourceId, String destinationId, long amount) {
        String id = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(id, sourceId, destinationId, amount);
        transactions.put(id, transaction);
        return transaction;
    }

    public Transaction get(String id) {
        validateId(id);
        return transactions.get(id);
    }

    public List<Transaction> getAll() {
        return new ArrayList<>(transactions.values());
    }

    public void delete(String id) {
        validateId(id);
        transactions.remove(id);
    }

    private void validateId(String id) {
        if (id == null || !transactions.containsKey(id)) {
            throw new IllegalArgumentException("Transaction not found.");
        }
    }

}
