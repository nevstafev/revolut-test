package home;

import home.app.Application;
import home.model.Account;
import home.resource.requests.CreateAccountRequest;
import org.junit.After;
import org.junit.Before;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public abstract class BaseTest {

    protected WebTarget baseClient = ClientBuilder.newClient().target("http://localhost:8080");
    private Application application;

    @Before
    public void setUpBaseTest() throws Exception {
        application = new Application();
        application.run();
    }

    @After
    public void tearDownBaseTest() throws Exception {
        application.shoutDown();
    }

    protected Account createAccount(long initialBalance) {
        return baseClient
                .path("accounts")
                .request()
                .post(Entity.entity(new CreateAccountRequest(initialBalance),
                        MediaType.APPLICATION_JSON), Account.class);
    }

    protected Account getAccountById(String id) {
        return baseClient
                .path("accounts/" + id)
                .request(MediaType.APPLICATION_JSON)
                .get(Account.class);
    }


    protected List<Account> getAccounts() {
        return baseClient
                .path("accounts")
                .request(MediaType.APPLICATION_JSON)
                .get()
                .readEntity(new GenericType<List<Account>>() {
                });
    }

}
