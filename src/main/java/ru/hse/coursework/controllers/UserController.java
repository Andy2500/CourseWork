package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Event.Event;
import ru.hse.coursework.models.Event.Events;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.UserInfo;
import ru.hse.coursework.models.User.UserProfile;
import ru.hse.coursework.models.User.Users;

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
                             @FormParam("phone") String phone,
                             @FormParam("new") String photo) {
        try {
            User.exists(login, phone, email);
            login = login.toLowerCase();
            email = email.toLowerCase();
            phone = phone.toLowerCase();

            String token = Service.makeToken(login + "10" + name + "10" + phone);
            new User(login, email, name, hashpsd, phone, token);

            User user = User.getUserByToken(token);
            User.setToken(user.getPersonID(), token);
            User.setLastOnlineDate(user.getPersonID());
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
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("last") String lastpsd,
                                  @FormParam("new") String newpsd) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                user.setHashpsd(user, newpsd, lastpsd);
                User.setLastOnlineDate(id);
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
                user.setEmail(user, newEmail, lastEmail);
                User.setLastOnlineDate(id);
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
                user.setPhone(user, newPhone, lastPhone);

                User.setLastOnlineDate(id);
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
                User.setPhoto(id, photo);
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
                User.setLogin(id, login);
                User.setLastOnlineDate(id);
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
                User.setName(id, name);

                User.setLastOnlineDate(id);
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

                User.setLastOnlineDate(id);
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
                User.setLastOnlineDate(id);
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

                User.setLastOnlineDate(id);
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
    @Path("/sul/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass saveUserLocation(@FormParam("token") String token,
                                         @FormParam("personID") int id,
                                         @FormParam("date") String date,
                                         @FormParam("latitude") float latitude,
                                         @FormParam("longitude") float longitude) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                User.setCoordinates(id, longitude, latitude);
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/sudp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass saveUserDocumentPhoto(@FormParam("token") String token,
                                              @FormParam("personID") int id,
                                              @FormParam("date") String date,
                                              @FormParam("documentPhoto") String documentPhoto) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                User.setDocumentPhoto(id, documentPhoto);
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/sfuwl/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Users searchForUserWithLogin(@FormParam("token") String token,
                                        @FormParam("personID") int id,
                                        @FormParam("date") String date,
                                        @FormParam("login") String login) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                login = login.toLowerCase();
                Users users = Users.getUsersWithLogin(login);
                users.setDefaultClass(new DefaultClass(true, ""));
                return users;
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            Users users = new Users();
            users.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return users;
        }
    }
}
