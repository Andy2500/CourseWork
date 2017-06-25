package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.models.User.Users;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/moder")
public class ModeratorController {
    /**
     * Метод для блокировки пользователя
     * Путь:  /moder/bu
     *
     * @param token  - токен пользователя
     * @param userID - ID пользователя для блокировки
     * @return DefaultClass
     */
    @POST
    @Path("/bu/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass blockUser(@FormParam("token") String token,
                                  @FormParam("userID") int userID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            User.setStatus(userID, -1);
            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    /**
     * Метод для подтверждения пользователя
     * Путь:  /moder/cu
     *
     * @param token  -
     * @param userID - ID пользователя для подтверждения
     * @return DefaultClass
     */
    @POST
    @Path("/cu/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass confirmUser(@FormParam("token") String token,
                                    @FormParam("userID") int userID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            User.setStatus(userID, 1);
            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    /**
     * Метод для получения списка пользователей, которые ждут подтверждения
     * Путь:  /moder/gufc/
     *
     * @param token - токен пользователя
     * @return массив сущностей User и DefaultClass
     */
    @POST
    @Path("/gufc/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Users getUsersForConfirm(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token, false, false, false);
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