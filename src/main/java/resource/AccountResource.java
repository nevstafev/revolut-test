package resource;


import model.Account;
import resource.requests.CreateAccountRequest;
import service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    @Inject
    private TransferService service;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(CreateAccountRequest request) {
        Account account = service.createAccount(request.getInitialAmount());
        return Response.ok(account, MediaType.APPLICATION_JSON).build();
    }
}
