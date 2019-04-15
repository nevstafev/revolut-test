package app;

import model.Account;
import service.AccountService;

import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        for (int i = 0; i < 10; i++) {
            accountService.getAllAccounts().add(new Account("account_" + i, 100));
        }

        for (Account account : accountService.getAllAccounts()) {
            new Thread(new Transaction(accountService, account)).start();
        }
    }

    static class Transaction implements Runnable {

        private final AccountService service;
        private final Account source;

        Transaction(AccountService service, Account source) {
            this.service = service;
            this.source =  source;
        }

        @Override
        public void run() {
            while (true) {
                Account destination = service.getAllAccounts().get((int) (Math.random() * service.getAllAccounts().size()));
                if(source.getId().equals(destination.getId())) {
                    continue;
                }
                service.transferMoney(source, destination, 10);
                System.out.printf("Transfer from %s to %s total is %s%n", source.getId(), destination.getId(),service.getAllAccounts().stream()
                        .map(Account::getBalance)
                        .reduce((a1, a2) -> a1 + a2));
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
