package com.imstudios.anntwit.api.core;

import com.imstudios.anntwit.appcore.CleanUpScheduler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/anntwit")
public class HelloResource {


    @GET
    @Produces("text/plain")
    @Path("testconnection")
    public String serverStatus() {
        CleanUpScheduler scheduler = new CleanUpScheduler();
        scheduler.setCleanUpActivity();
        return "Server is UP!";
    }

}