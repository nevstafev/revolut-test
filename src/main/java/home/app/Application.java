package home.app;

import home.dao.AccountDao;
import home.dao.TransactionDao;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import home.resource.AccountResource;
import home.resource.TransactionResource;
import home.resource.TransferResource;
import home.service.TransferService;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
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
        HttpServer server = HttpServer.createSimpleServer("http://localhost", 8080);
        server.getServerConfiguration().addHttpHandler(endpoint);
        try {
            server.start();
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}