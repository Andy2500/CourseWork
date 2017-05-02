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
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("personID") int personID,
                                     @FormParam("text") String text,
                                     @FormParam("mark") int mark,
                                     @FormParam("packageID") int packageID) {
        try {

            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                User.setLastOnlineDate(id);
                new Response(user.getPersonID(), personID, text, mark);
                User.addMark(personID, mark);

                return new DefaultClass(true, token);
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gri/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResponseInfo(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("responseID") int responseID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Response response = Response.getResponseByID(responseID);

                User.setLastOnlineDate(id);
                response.setDefaultClass(new DefaultClass(true, token));
                return response;
            }

            throw new Exception("token error");
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
                                        @FormParam("personID") int id,
                                        @FormParam("date") String date,
                                        @FormParam("responseID") int responseID,
                                        @FormParam("text") String text) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new Comment(responseID, user.getPersonID(), text);

                User.setLastOnlineDate(id);
                return new DefaultClass(true, token);
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dc/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteComment(@FormParam("token") String token,
                                      @FormParam("personID") int id,
                                      @FormParam("date") String date,
                                      @FormParam("commentID") int commentID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                User.setLastOnlineDate(id);
                Comment.deleteComment(commentID);
                return new DefaultClass(true, token);
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }
}
