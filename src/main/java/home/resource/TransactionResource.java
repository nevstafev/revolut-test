package home.resource;

import home.service.TransferService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    private TransferService service;

    @GET
    @Path("/{id}")
    public Response getTransaction(@PathParam("id") String id) {
        return Response.ok(service.getTransaction(id)).build();
    }

    @GET
    public Response getTransactions() {
        return Response.ok(service.getTransactions()).build();
    }
}
