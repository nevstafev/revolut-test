package home;

import home.app.Application;
import home.model.Account;
import home.resource.requests.CreateAccountRequest;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class BaseTest {

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



}
