package com.imstudios.anntwit.api.user;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/app")
public class UserController {

    UserService userService = UserService.getInstance();

    @GET
    @Produces("userinfo/json")
    @Consumes("userinfo/json")
    @Path("user")
    public Response singIn(HashMap<String, String> userData) {
        String userName = userData.get("userName");
        String password = userData.get("password");
        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            return Response.status(409, "Some data is not in required format").build();
        }
        if (!isUserExists(userName)) {
            return Response.status(409, "User Name Incorrect").build();
        }
        Map<String, String> map = new HashMap<String, String>() {{
            put("status", userService.validateUserLogin(userName,password) ? "Success" : "Failed");
        }};
        return Response.ok(map, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces("userinfo/json")
    @Consumes("userinfo/json")
    @Path("user")
    public Response singUp(HashMap<String, String> userData) {
        String userName = userData.get("userName");
        String emailID = userData.get("email");
        String password = userData.get("password");
        if (isUserExists(userName)) {
            Response.status(409, "User name already taken in AnnTwit System").build();// conflict
        }
        boolean status = userService.createUser(userName, password, emailID);
        userData.put("status", status ? "Success" : "Failed");
        return Response.ok(userData, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Produces("userinfo/json")
    @Consumes("userinfo/json")
    @Path("user")
    public Response deleteUser(HashMap<String, String> userData) {
        String userName = userData.get("userName");
        String password = userData.get("password");
        if (!isUserExists(userName)) {
            Response.status(409, "User doesn't exists in AnnTwit System").build();// conflict
        }
        boolean status = userService.deleteUser(userName, password);
        userData.put("status", status ? "Success" : "Failed");
        return Response.ok(userData, MediaType.APPLICATION_JSON).build();
    }

    private boolean isUserExists(String userName) {
        return userService.isUserExists(userName);
    }


}
