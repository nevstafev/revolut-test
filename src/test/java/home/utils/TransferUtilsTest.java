package home.utils;

import home.model.Account;
import org.hamcrest.Matcher;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class TransferUtilsTest {

    public static final long BALANCE = 10000;
    public static final long AMOUNT = 1000;
    TransferUtils transfer = new TransferUtils();

    @Test
    public void testTransfer() throws Exception {
        Account source = new Account("1", BALANCE);
        Account destination = new Account("2", BALANCE);

        transfer.transferMoney(source, destination, AMOUNT);

        Assert.assertThat(source.getBalance(), Is.is(BALANCE - AMOUNT));
        Assert.assertThat(destination.getBalance(), Is.is(BALANCE + AMOUNT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferToTheSameAccount() {
        Account account = new Account("1", BALANCE);

        transfer.transferMoney(account, account, AMOUNT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferAmountGreaterThanBalance() {
        Account source = new Account("1", BALANCE);
        Account destination = new Account("2", BALANCE);

        transfer.transferMoney(source, destination, BALANCE * 2);
    }
}