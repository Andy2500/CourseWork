package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.Response.Comment;
import ru.hse.coursework.models.Response.Response;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/res")
public class ResponseController {

    /**
     * Путь:  /res/mr
     *
     * @param token    - токен пользователя
     * @param personID - ID пользователя
     * @param text     - текст
     * @param mark     - оценка
     * @return DefaultClass
     */
    @POST
    @Path("/mr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeResponse(@FormParam("token") String token,
                                     @FormParam("personID") int personID,
                                     @FormParam("text") String text,
                                     @FormParam("mark") int mark) {
        try {
            User user = User.getUserByToken(token);
            new Response(user.getPersonID(), personID, text, mark);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Путь:  /res/gri
     *
     * @param token      - токен пользователя
     * @param responseID - ID отзыва
     * @return Сущность Response
     */
    @POST
    @Path("/gri/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResponseInfo(@FormParam("token") String token,
                                    @FormParam("responseID") int responseID) {
        try {
            User.getUserByToken(token);
            Response response = Response.getResponseByID(responseID);

            response.setDefaultClass(new DefaultClass(true, token));
            return response;

        } catch (Exception ex) {
            Response response = new Response();
            response.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return response;
        }
    }

    /**
     * Путь:  /res/cr
     *
     * @param token      - токен пользователя
     * @param responseID - ID отзыва
     * @param text       - текст комментария
     * @return DefaultClass
     */
    @POST
    @Path("/cr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass commentResponse(@FormParam("token") String token,
                                        @FormParam("responseID") int responseID,
                                        @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            new Comment(responseID, user.getPersonID(), text);

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, token);

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Путь:  /res/dc
     *
     * @param token     - токен пользователя
     * @param commentID - ID комментария
     * @return DefaultClass
     */
    @POST
    @Path("/dc/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteComment(@FormParam("token") String token,
                                      @FormParam("commentID") int commentID) {
        try {
            User user = User.getUserByToken(token);

            User.setLastOnlineDate(user.getPersonID());
            Comment.deleteComment(commentID);
            return new DefaultClass(true, token);

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }
}
