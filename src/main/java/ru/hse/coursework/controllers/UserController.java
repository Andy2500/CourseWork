package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.UserProfile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserController {

    @GET
    @Path("/reg/l={login}&h={hashpsd}&m={email}&n={name}&p={phone}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass register(@PathParam("login") String login,
                                 @PathParam("hashpsd") String hashpsd,
                                 @PathParam("email") String email,
                                 @PathParam("name") String name,
                                 @PathParam("phone") String phone) {
        try {
            User user = new User(login, email, name, hashpsd, phone);
            String token = Service.makeToken(user.getLogin());
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            DefaultClass defaultClass = new DefaultClass(false, ex.getMessage());
            return defaultClass;
        }
    }

    @GET
    @Path("/log/l={login}&h={hashpsd}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass login(@PathParam("login") String login, @PathParam("hashpsd") String hashpsd) {
        try {
            User user = User.getUserByLogin(login);
            String token = Service.makeToken(user.getLogin());
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/chpsd/t={token}&l={last}&n={new}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePsd(@PathParam("token") String token,
                                  @PathParam("last") String lastpsd,
                                  @PathParam("new") String newpsd) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setHashpsd(newpsd, lastpsd);
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/chm/t={token}&n={new}&n={last}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeEmail(@PathParam("token") String token,
                                    @PathParam("last") String lastEmail,
                                    @PathParam("new") String newEmail) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setEmail(newEmail, lastEmail);
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/chphon/t={token}&n={new}&n={last}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePhone(@PathParam("token") String token,
                                    @PathParam("last") String lastPhone,
                                    @PathParam("new") String newPhone) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setPhone(newPhone, lastPhone);
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /*
    @GET
    @Path("/chphot/t={token}&n={new}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePhoto(@PathParam("token") String token,
                                    @PathParam("new") String photo) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setPhoto(photo);
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    */

    @GET
    @Path("/chlog/t={token}&n={new}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeLogin(@PathParam("token") String token,
                                    @PathParam("new") String login) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setLogin(login);
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/chname/t={token}&n={new}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeName(@PathParam("token") String token,
                                   @PathParam("new") String name) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setName(name);
            user.setToken(token);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/gupf/t={token}&id={ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserProfile(@PathParam("token") String token,
                                      @PathParam("ID") int ID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            UserProfile userProfile = UserProfile.getUserProfileByID(ID);
            userProfile.setDefaultClass(new DefaultClass(true, token));
            return userProfile;
        } catch (Exception ex) {
            UserProfile userProfile = new UserProfile();
            userProfile.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return userProfile;
        }
    }

    @GET
    @Path("/gpf/t={token}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getProfile(@PathParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            UserProfile userProfile = UserProfile.getUserProfileByUser(user);
            userProfile.setDefaultClass(new DefaultClass(true, token));
            return userProfile;
        } catch (Exception ex) {
            UserProfile userProfile = new UserProfile();
            userProfile.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return userProfile;
        }
    }
}
