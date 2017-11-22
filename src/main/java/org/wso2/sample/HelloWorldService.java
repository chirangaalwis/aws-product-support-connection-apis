package org.wso2.sample;

import org.wso2.sample.sns.AWSSNSReceiver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/hello")
public class HelloWorldService {

    // AWS credentials -- replace with your credentials
    private static String ACCESS_KEY = "<XXXXXXXX>";
    private static String SECRET_KEY = "<XXXXXXXXXXXX>";

    @GET
    @Path("/{name}")
    public String hello(@PathParam("name") String name) {
        try {
            AWSSNSReceiver.receiveData(ACCESS_KEY, SECRET_KEY);
        } catch (Exception exception) {
            System.out.println("Error occurred...");

            return "Failure...";
        }

        return "Success... " + name;
    }
}
