package home.resource;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import home.resource.requests.TransferRequest;
import home.service.TransferService;

@Path("/transfer")
public class TransferResource {

    @Inject
    private TransferService service;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transferMoney(TransferRequest request) {
        return Response.ok(service.createTransferRequest(request.getSourceAccountId(),
                request.getDestinationAccountId(),
                request.getAmount())).build();
    }
}
