package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.UserProfile;
import ru.hse.coursework.models.User.Users;
import ru.hse.coursework.service.TokenBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserController {

    /**
     * Метод для регистрации пользователей
     * Путь:  /user/reg/
     *
     * @param login   - логин пользователя
     * @param hashpsd - MD5-хеш пароля пользователя
     * @param email   - почта пользователя
     * @param name    - имя пользователя
     * @param phone   - телефон пользователя
     * @return DefaultClass
     */
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
            email = email.toLowerCase();
            phone = phone.toLowerCase();

            String token = TokenBuilder.makeToken(login + "10" + name + "10" + phone);
            new User(login, email, name, hashpsd, phone, token);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для авторизации пользователя
     * Путь:  /user/log/
     *
     * @param login   - логин пользователя
     * @param hashpsd - MD5-хеш пароля пользователя
     * @return DefaultClass
     */
    @POST
    @Path("/log/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass login(@FormParam("login") String login, @FormParam("hashpsd") String hashpsd) {
        try {
            User user = User.getUserByLogin(login);

            if (!user.getHashpsd().equals(hashpsd)) {
                throw new Exception("Неверный пароль");
            }

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, user.getToken());
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для смены пароля
     * Путь:  /user/chpsd/
     *
     * @param token   - токен пользователя
     * @param lastpsd - MD5-хеш предыдущего значения пароля пользователя
     * @param newpsd  - MD5-хеш значения пароля пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для смены адреса электронной почты
     * Путь:  /user/chm/
     *
     * @param token     - токен пользователя
     * @param lastEmail - предыдущего значение адреса электронной почты пользователя
     * @param newEmail  - новое значение адреса электронной почты пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для смены номера телефона
     * Путь:  /user/chphon/
     *
     * @param token     - токен пользователя
     * @param lastPhone - предыдущее значения телефона пользователя
     * @param newPhone  - новое значения телефона пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для изменения фотографии пользователя
     * Путь:  /user/chphot/
     *
     * @param token - токен пользователя
     * @param photo - новое фото пользователя в формате строки base64
     * @return DefaultClass
     */
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


    /**
     * Метод для изменения логина пользователя
     * Путь:  /user/chlog/
     *
     * @param token - токен пользователя
     * @param login - логин пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для смены имени пользователя
     * Путь:  /user/chname/
     *
     * @param token - токен пользователя
     * @param name  - новое имя пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для получения профиля пользователя
     * Путь:  /user/gupf/
     *
     * @param token    - токен пользователя
     * @param personID - ID пользователя
     * @return Сущность User и DefaultClass
     */
    @POST
    @Path("/gupf/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public UserProfile getUserProfile(@FormParam("token") String token,
                                      @FormParam("personID") int personID) {
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

    /**
     * Метод для получения своего профиля
     * Путь:  /user/gpf/
     *
     * @param token - токен пользователя
     * @return Cущность User и DefaultClass
     */
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

    /**
     * Метод для сохранения последней локации пользователя
     * Путь:  /user/sul/
     *
     * @param token     - токен пользователя
     * @param latitude  - широта местонахождения пользователя
     * @param longitude - долгота местонахождения пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для загрузки фото документа пользователя
     * Путь:  /user/sudp/
     *
     * @param token         - токен пользователя
     * @param documentPhoto - фото документа пользователя
     * @return DefaultClass
     */
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

    /**
     * Метод для поиска пользователей по логину
     * Путь:  /user/sfuwl/
     *
     * @param token - токен пользователя
     * @param login - логин пользователя для поиска
     * @return Массив из сущностей User и DefaultClass
     */
    @POST
    @Path("/sfuwl/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Users searchForUserWithLogin(@FormParam("token") String token,
                                        @FormParam("login") String login) {
        try {
            User.getUserByToken(token);

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