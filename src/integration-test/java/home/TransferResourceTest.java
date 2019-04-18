package home;

import home.model.Account;
import home.model.Transaction;
import home.resource.requests.TransferRequest;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TransferResourceTest extends BaseTest {

    public static final long INITIAL_BALANCE = 100_00;
    public static final long TRANSFER_AMOUNT = 10_00;

    WebTarget transferClient = baseClient.path("transfer");

    @Test
    public void simpleTransferTest() {
        Account sourceAccount = createAccount(INITIAL_BALANCE);
        Account destinationAccount = createAccount(INITIAL_BALANCE);

        Transaction transaction = createTransaction(sourceAccount.getId(), destinationAccount.getId(), TRANSFER_AMOUNT);

        Account firstAccountAfterTransfer = getAccountById(sourceAccount.getId());
        Account secondAccountAfterTransfer = getAccountById(destinationAccount.getId());

        Assert.assertThat(transaction.getStatus(), Is.is(Transaction.FINISHED));
        Assert.assertThat(transaction.getAmount(), Is.is(TRANSFER_AMOUNT));
        Assert.assertThat(transaction.getSourceId(), Is.is(sourceAccount.getId()));
        Assert.assertThat(transaction.getDestinationId(), Is.is(destinationAccount.getId()));
        Assert.assertThat(firstAccountAfterTransfer.getBalance(), Is.is(INITIAL_BALANCE - TRANSFER_AMOUNT));
        Assert.assertThat(secondAccountAfterTransfer.getBalance(), Is.is(INITIAL_BALANCE + TRANSFER_AMOUNT));
    }

    @Test
    public void testTransferWithAmountGreaterThanAccountBalance() {
        long zeroInitialBalance = 0;

        Account accountWithZeroBalance = createAccount(zeroInitialBalance);
        Account destinationAccount = createAccount(INITIAL_BALANCE);

        Transaction transaction = createTransaction(accountWithZeroBalance.getId(), destinationAccount.getId(), TRANSFER_AMOUNT);

        Account accountWithZeroBalanceAfterTransfer = getAccountById(accountWithZeroBalance.getId());
        Account destinationAccountAfterTransfer = getAccountById(destinationAccount.getId());

        Assert.assertThat(transaction.getStatus(), Is.is(Transaction.FAILED));
        Assert.assertThat(transaction.getAmount(), Is.is(TRANSFER_AMOUNT));
        Assert.assertThat(accountWithZeroBalanceAfterTransfer.getBalance(), Is.is(zeroInitialBalance));
        Assert.assertThat(destinationAccountAfterTransfer.getBalance(), Is.is(INITIAL_BALANCE));
    }

    @Test
    public void testTransferWithLoad() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        Random random = new Random(System.currentTimeMillis());
        List<Account> accounts = new ArrayList<>();
        List<Future> transactionTasks = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            accounts.add(createAccount(INITIAL_BALANCE));
        }

        for (Account account : accounts) {
            transactionTasks.add(executorService.submit(() -> {
                createTransaction(account.getId(),
                        accounts.get(random.nextInt(accounts.size())).getId(),
                        10_00);
            }));
        }
        for (Future transactionTask : transactionTasks) {
            transactionTask.get();
        }

        List<Account> accountsAfterTransaction = getAccounts();
        Long totalBalanceAfterTransactions = accountsAfterTransaction.stream()
                .map(Account::getBalance)
                .reduce((accBalance, sum) -> sum += accBalance).get();

        Assert.assertThat(totalBalanceAfterTransactions, Is.is((INITIAL_BALANCE * accounts.size())));
    }

    private Transaction createTransaction(String sourceAccountId, String destinationAccountId, long transferAmount) {
        return transferClient
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(new TransferRequest(sourceAccountId,
                        destinationAccountId,
                        transferAmount), MediaType.APPLICATION_JSON), Transaction.class);
    }
}
