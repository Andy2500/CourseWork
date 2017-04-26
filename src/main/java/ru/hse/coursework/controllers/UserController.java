package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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
                             @FormParam("phone") String phone) {
        try {
            User.exists(login, phone, email);
            login = login.toLowerCase();
            email = email.toLowerCase();
            phone = phone.toLowerCase();

            User user = new User(login, email, name, hashpsd, phone);
            String token = Service.makeToken(user.getLogin() + "10" + user.getName() + "10" + user.getEmail());
            user = User.getUserByLogin(login);
            user.setLastOnlineDate();
            Event.writeEvent("Вы зарегистрировались!", user.getPersonID());

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

            user.setLastOnlineDate();
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
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("last") String lastpsd,
                                  @FormParam("new") String newpsd) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setHashpsd(newpsd, lastpsd);
                user.setLastOnlineDate();
                Event.writeEvent("Вы изменили пароль!", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chm/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeEmail(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("last") String lastEmail,
                                    @FormParam("new") String newEmail) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setEmail(newEmail, lastEmail);

                user.setLastOnlineDate();
                Event.writeEvent("Вы изменили почту!", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chphon/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePhone(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("last") String lastPhone,
                                    @FormParam("new") String newPhone) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setPhone(newPhone, lastPhone);

                user.setLastOnlineDate();
                Event.writeEvent("Вы изменили номер телефона!", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chphot/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changePhoto(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("new") String photo) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setPhoto(photo);

                Event.writeEvent("Вы изменили фотографию профиля!", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    @POST
    @Path("/chlog/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeLogin(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("new") String login) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setLogin(login);

                user.setLastOnlineDate();
                Event.writeEvent("Вы поменяли свой логин!", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/chname/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass changeName(@FormParam("token") String token,
                                   @FormParam("personID") int id,
                                   @FormParam("date") String date,
                                   @FormParam("new") String name) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setName(name);

                user.setLastOnlineDate();
                Event.writeEvent("Вы изменили имя!", user.getPersonID());
                return new DefaultClass(true, "");
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gupf/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserProfile(@FormParam("token") String token,
                                      @FormParam("personID") int id,
                                      @FormParam("date") String date,
                                      @FormParam("userID") int personID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                UserProfile userProfile = UserProfile.getUserProfileByID(personID);
                userProfile.setDefaultClass(new DefaultClass(true, ""));

                user.setLastOnlineDate();
                return userProfile;
            }
            throw new Exception("token error");
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
    public UserProfile getProfile(@FormParam("token") String token,
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                UserProfile userProfile = UserProfile.getUserProfileByUser(user);
                userProfile.setDefaultClass(new DefaultClass(true, ""));
                user.setLastOnlineDate();
                return userProfile;
            }
            throw new Exception("token error");
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
    public Events getEvents(@FormParam("token") String token,
                            @FormParam("personID") int id,
                            @FormParam("date") String date,
                            @FormParam("event") String lastDate) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Events events = new Events(Event.getEventsByPersonIDFromDate(user.getPersonID(), Service.get3DayBeforeDate(Service.dateFromString(lastDate))), new DefaultClass(true, ""));

                user.setLastOnlineDate();
                return events;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Events events = new Events();
            events.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            events.setEvents(new ArrayList<Event>());
            return events;
        }
    }

    @POST
    @Path("/gupho/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserPhoto getUserPhoto(@FormParam("token") String token,
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("userID") int userID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                String photo = User.getUserPhoto(userID);
                user.setLastOnlineDate();
                return new UserPhoto(photo, new DefaultClass(true, ""));
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            return new UserPhoto(null, new DefaultClass(false, ""));
        }
    }
}
