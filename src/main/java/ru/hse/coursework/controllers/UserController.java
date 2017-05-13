package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.UserInfo;
import ru.hse.coursework.models.User.UserProfile;
import ru.hse.coursework.models.User.Users;
import ru.hse.coursework.service.TokenBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserController {

    @POST
    @Path("/reg/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo register(@FormParam("login") String login,
                             @FormParam("hashpsd") String hashpsd,
                             @FormParam("email") String email,
                             @FormParam("name") String name,
                             @FormParam("phone") String phone,
                             @FormParam("new") String photo) {
        try {
            User.exists(login, phone, email);
            login = login.toLowerCase();
            email = email.toLowerCase();
            phone = phone.toLowerCase();

            String token = TokenBuilder.makeToken(login + "10" + name + "10" + phone);
            new User(login, email, name, hashpsd, phone, token);

            User user = User.getUserByToken(token);
            User.setToken(user.getPersonID(), token);
            User.setLastOnlineDate(user.getPersonID());

            return new UserInfo(user.getPersonID(), new DefaultClass(true, user.getToken()));
        } catch (Exception ex) {
            return new UserInfo(0, new DefaultClass(false, ex.getMessage()));
        }
    }

    @POST
    @Path("/log/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo login(@FormParam("login") String login, @FormParam("hashpsd") String hashpsd) {
        try {
            User user = User.getUserByLogin(login);

            if (!user.getHashpsd().equals(hashpsd)) {
                throw new Exception("hashpsd error");
            }

            User.setLastOnlineDate(user.getPersonID());
            return new UserInfo(user.getPersonID(), new DefaultClass(true, user.getToken()));
        } catch (Exception ex) {
            return new UserInfo(0, new DefaultClass(false, ex.getMessage()));
        }
    }

    @POST
    @Path("/chpsd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePsd(@FormParam("token") String token,
                                  @FormParam("last") String lastpsd,
                                  @FormParam("new") String newpsd) {
        try {
            User user = User.getUserByToken(token);
            User.setHashpsd(user, newpsd, lastpsd);
            return new DefaultClass(true, "");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chm/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeEmail(@FormParam("token") String token,
                                    @FormParam("last") String lastEmail,
                                    @FormParam("new") String newEmail) {
        try {
            User user = User.getUserByToken(token);
            User.setEmail(user, newEmail, lastEmail);

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chphon/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePhone(@FormParam("token") String token,
                                    @FormParam("last") String lastPhone,
                                    @FormParam("new") String newPhone) {
        try {
            User user = User.getUserByToken(token);
            User.setPhone(user, newPhone, lastPhone);

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chphot/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePhoto(@FormParam("token") String token,
                                    @FormParam("new") String photo) {
        try {
            User user = User.getUserByToken(token);
            User.setPhoto(user.getPersonID(), photo);

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    @POST
    @Path("/chlog/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeLogin(@FormParam("token") String token,
                                    @FormParam("new") String login) {
        try {
            User user = User.getUserByToken(token);
            User.setLogin(user.getPersonID(), login);

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chname/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeName(@FormParam("token") String token,
                                   @FormParam("new") String name) {
        try {
            User user = User.getUserByToken(token);
            User.setName(user.getPersonID(), name);

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gupf/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserProfile(@FormParam("token") String token,
                                      @FormParam("userID") int personID) {
        try {
            User.getUserByToken(token);
            UserProfile userProfile = UserProfile.getUserProfileByID(personID);
            userProfile.setDefaultClass(new DefaultClass(true, ""));

            return userProfile;

        } catch (Exception ex) {
            UserProfile userProfile = new UserProfile();
            userProfile.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return userProfile;
        }
    }

    @POST
    @Path("/gpf/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getProfile(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            UserProfile userProfile = UserProfile.getUserProfileByUser(user);
            userProfile.setDefaultClass(new DefaultClass(true, ""));

            return userProfile;

        } catch (Exception ex) {
            UserProfile userProfile = new UserProfile();
            userProfile.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return userProfile;
        }
    }

    @POST
    @Path("/sul/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass saveUserLocation(@FormParam("token") String token,
                                         @FormParam("latitude") float latitude,
                                         @FormParam("longitude") float longitude) {
        try {
            User user = User.getUserByToken(token);
            User.setCoordinates(user.getPersonID(), longitude, latitude);
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/sudp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass saveUserDocumentPhoto(@FormParam("token") String token,
                                              @FormParam("documentPhoto") String documentPhoto) {
        try {
            User user = User.getUserByToken(token);
            User.setDocumentPhoto(user.getPersonID(), documentPhoto);
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/sfuwl/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Users searchForUserWithLogin(@FormParam("token") String token,
                                        @FormParam("login") String login) {
        try {
            User.getUserByToken(token);

            login = login.toLowerCase();
            Users users = Users.getUsersWithLogin(login);
            users.setDefaultClass(new DefaultClass(true, ""));
            return users;

        } catch (Exception ex) {
            Users users = new Users();
            users.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return users;
        }
    }
}