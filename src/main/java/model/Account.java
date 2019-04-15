package model;

public class Account {
    private String id;
    private long balance;

    public Account(String id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
