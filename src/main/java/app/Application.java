package app;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import service.RestService;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(RestService.class);

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