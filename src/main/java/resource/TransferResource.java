package resource;


import model.Account;
import service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class TransferResource {

    @Inject
    private TransferService service;

    @GET
    @Path("/accounts")
    public Response getAccounts() {
        return Response.ok(service.getAccounts()).build();
    }

    @GET
    @Path("/accounts/{id}")
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
    @Path("/accounts")
    @Consumes("application/json")
    public Response createAccount(@PathParam("amount") long amount) {
        Account account = service.createAccount(amount);
        return Response.ok(account).build();
    }

    @PUT
    @Path("/transfer")
    @Consumes("application/json")
    public Response transferMoney(@PathParam("sourceAccountId") String sourceAccountId,
                                  @PathParam("destinationAccountId") String destinationAccountId,
                                  @PathParam("amount") long amount) {
        return Response.ok(service.createTransferRequest(sourceAccountId, destinationAccountId, amount)).build();
    }

    @GET
    @Path("/transactions/{id}")
    public Response getTransaction(@PathParam("id") String id) {
        return Response.ok(service.getTransaction(id)).build();
    }

    @GET
    @Path("/transactions")
    public Response getTransactions() {
        return Response.ok(service.getTransactions()).build();
    }
}
