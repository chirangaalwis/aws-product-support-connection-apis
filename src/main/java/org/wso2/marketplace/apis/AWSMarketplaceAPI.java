package org.wso2.marketplace.apis;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/service")
public class AWSMarketplaceAPI {
    @POST
    @Path("/")
    public void post() {
        // TODO: Implementation for HTTP POST request
        System.out.println("POST invoked");
    }
}
