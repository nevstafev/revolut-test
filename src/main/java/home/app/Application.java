package home.app;

import home.dao.AccountDao;
import home.dao.TransactionDao;
import home.resource.AccountResource;
import home.resource.TransactionResource;
import home.resource.TransferResource;
import home.service.TransferService;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;

public class Application {

    private HttpServer server;

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.run();
        System.in.read();
        application.shoutDown();
    }

    public void run() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(TransferResource.class);
        resourceConfig.register(AccountResource.class);
        resourceConfig.register(TransactionResource.class);
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(new TransferService(new AccountDao(), new TransactionDao())).to(TransferService.class);
            }
        });

        HttpHandler endpoint = RuntimeDelegate.getInstance().createEndpoint(resourceConfig, HttpHandler.class);
        server = HttpServer.createSimpleServer("http://localhost", 8080);
        server.getServerConfiguration().addHttpHandler(endpoint);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shoutDown() {
        server.shutdown();
    }
}