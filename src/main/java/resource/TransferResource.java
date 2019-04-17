package resource;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Account;
import resource.requests.CreateAccountRequest;
import resource.requests.TransferRequest;
import service.TransferService;

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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAccount(CreateAccountRequest request) {
        Account account = service.createAccount(request.getInitialAmount());
        return Response.ok(account).build();
    }

    @PUT
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transferMoney(TransferRequest request) {
        return Response.ok(service.createTransferRequest(request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getAmount())).build();
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
