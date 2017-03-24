package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Response.Comment;
import ru.hse.coursework.models.Response.Response;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/res")
public class ResponseController {

    @POST
    @Path("/mr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeResponse(@FormParam("token") String token,
                                     @FormParam("personID") int personID,
                                     @FormParam("text") String text,
                                     @FormParam("mark") int mark) {
        try {
            User critic = User.getUserByToken(token);
            token = Service.makeToken(critic.getLogin());
            critic.setToken(token);
            critic.setLastOnlineDate();
            new Response(critic.getPersonID(), personID, text, mark);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gri/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResponseInfo(@FormParam("token") String token,
                                    @FormParam("responseID") int responseID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Response response = Response.getResponseByID(responseID);

            user.setToken(token);
            user.setLastOnlineDate();
            response.setDefaultClass(new DefaultClass(true, token));
            return response;
        } catch (Exception ex) {
            Response response = new Response();
            response.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return response;
        }
    }

    @POST
    @Path("/cr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass commentResponse(@FormParam("token") String token,
                                        @FormParam("responseID") int responseID,
                                        @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            new Comment(responseID, user.getPersonID(), text);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dc/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteComment(@FormParam("token") String token,
                                      @FormParam("commentID") int commentID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);
            user.setLastOnlineDate();
            Comment.deleteComment(commentID);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }
}
