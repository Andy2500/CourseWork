package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.Users;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/moder")
public class ModeratorController {
    @POST
    @Path("/bu/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass blockUser(@FormParam("token") String token,
                                  @FormParam("userID") int userID) {
        try {
            User user = User.getUserByToken(token);
            User.setStatus(userID, -1);
            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/cu/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass confirmUser(@FormParam("token") String token,
                                    @FormParam("userID") int userID) {
        try {
            User user = User.getUserByToken(token);
            User.setStatus(userID, 1);
            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/gufc/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Users getUsersForConfirm(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            Users users = Users.getUsersForConfirm();
            User.setLastOnlineDate(user.getPersonID());
            users.setDefaultClass(new DefaultClass(true, ""));
            return users;

        } catch (Exception ex) {
            Users users = new Users();
            users.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return users;
        }
    }
}