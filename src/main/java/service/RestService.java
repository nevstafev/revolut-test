package service;


import model.Account;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class RestService {

    @Inject
    private AccountService service;

    @GET
    public Response getAccounts() {
        return Response.ok(service.getAccounts()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAccountById(@PathParam("id") String id) {
        Account account;
        try {
            account = service.getAccountById(id);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(account).build();
    }

    @POST
    @Consumes("application/json")
    public Response createAccount(@PathParam("amount") long amount) {
        Account account = service.createNewAccount(amount);
        return Response.ok(account).build();
    }
}
