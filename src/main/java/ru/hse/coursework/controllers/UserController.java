package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.Event;
import ru.hse.coursework.models.User.Events;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.UserProfile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;

@Path("/user")
public class UserController {

    @POST
    @Path("/reg/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass register(@FormParam("login") String login,
                                 @FormParam("hashpsd") String hashpsd,
                                 @FormParam("email") String email,
                                 @FormParam("name") String name,
                                 @FormParam("phone") String phone) {
        try {
            User.exists(login, phone, email);
            login = login.toLowerCase();
            email = email.toLowerCase();
            phone = phone.toLowerCase();
            User user = new User(login, email, name, hashpsd, phone);
            String token = Service.makeToken(user.getLogin());
            user = User.getUserByToken("");
            user.setToken(token);
            user.setLastOnlineDate();

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/log/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass login(@FormParam("login") String login, @FormParam("hashpsd") String hashpsd) {
        try {
            User user = User.getUserByLogin(login);
            if (!user.getHashpsd().equals(hashpsd)) {
                throw new Exception("Hashpsd error");
            }
            String token = Service.makeToken(user.getLogin());
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
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
            token = Service.makeToken(user.getLogin());
            user.setHashpsd(newpsd, lastpsd);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
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
            token = Service.makeToken(user.getLogin());
            user.setEmail(newEmail, lastEmail);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
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
            token = Service.makeToken(user.getLogin());
            user.setPhone(newPhone, lastPhone);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
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
            token = Service.makeToken(user.getLogin());
            user.setPhoto(photo);
            user.setToken(token);
            return new DefaultClass(true, token);
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
            token = Service.makeToken(user.getLogin());
            user.setLogin(login);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
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
            token = Service.makeToken(user.getLogin());
            user.setName(name);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gupf/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserProfile(@FormParam("token") String token,
                                      @FormParam("personID") int personID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            UserProfile userProfile = UserProfile.getUserProfileByID(personID);
            userProfile.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
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
            token = Service.makeToken(user.getLogin());
            UserProfile userProfile = UserProfile.getUserProfileByUser(user);
            userProfile.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return userProfile;
        } catch (Exception ex) {
            UserProfile userProfile = new UserProfile();
            userProfile.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return userProfile;
        }
    }

    @POST
    @Path("/ge/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Events getEvents(@FormParam("token") String token, @FormParam("date") Date lastDate) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Events events = new Events(Event.getEventsByPersonIDFromDate(user.getPersonID(), Service.get3DayBeforeDate(lastDate)), new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return events;
        } catch (Exception ex) {
            Events events = new Events();
            events.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            events.setEvents(new ArrayList<Event>());
            return events;
        }
    }
}