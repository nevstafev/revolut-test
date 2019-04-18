package home;

import home.model.Account;
import home.resource.requests.CreateAccountRequest;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class AccountResourceTest extends BaseTest {

    private WebTarget client;

    @Before
    public void setUp() throws Exception {
        client = ClientBuilder.newClient().
                target("http://localhost:8080")
                .path("/accounts");
    }

    @Test
    public void testCreateAccount() {
        long initialAmount = 100_00;

        Account account = createAccount(initialAmount);

        Assert.assertNotNull(account);
        Assert.assertNotNull(account.getId());
        Assert.assertThat(account.getBalance(), Is.is(initialAmount));
    }

    @Test
    public void testGetAccountById() {
        Account account = createAccount(0);

        Account accountById = client
                .path(account.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Account.class);

        Assert.assertThat(accountById.getId(), Is.is(account.getId()));
        Assert.assertThat(accountById.getBalance(), Is.is(account.getBalance()));
    }

    private Account createAccount(long i) {
        return client.request().post(Entity.entity(new CreateAccountRequest(i),
                MediaType.APPLICATION_JSON), Account.class);
    }

    @Test
    public void testGetAllAccounts() {
        List<Account> createdAccounts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            createdAccounts.add(createAccount(i));
        }

        List<Account> accounts = client.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<Account>>());

        Assert.assertThat(accounts.size(), Is.is(createdAccounts.size()));
        Assert.assertTrue(accounts.containsAll(createdAccounts));
    }
}
