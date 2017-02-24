package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Response.Comment;
import ru.hse.coursework.models.Response.Response;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/res")
public class ResponseController {

    @POST
    @Path("/mr/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeResponse(@HeaderParam("token") String token,
                                     @HeaderParam("personID") int personID,
                                     @HeaderParam("text") String text,
                                     @HeaderParam("mark") int mark) {
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
    @Path("/grby/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResponseInfo(@HeaderParam("token") String token,
                                    @HeaderParam("ID") int ID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Response response = Response.getResponseByID(ID);
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
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass commentResponse(@HeaderParam("token") String token,
                                        @HeaderParam("responseID") int responseID,
                                        @HeaderParam("text") String text) {
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
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteComment(@HeaderParam("token") String token,
                                      @HeaderParam("commentID") int commentID) {
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

    //    @GET
//    @Path("/mr/t={token}&p={personID}&t={text}&m={mark}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass makeResponse(@PathParam("token") String token,
//                                     @PathParam("personID") int personID,
//                                     @PathParam("text") String text,
//                                     @PathParam("mark") int mark) {
//        try {
//            User critic = User.getUserByToken(token);
//            token = Service.makeToken(critic.getLogin());
//            critic.setToken(token);
//            critic.setLastOnlineDate();
//            new Response(critic.getPersonID(), personID, text, mark);
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/grby/t={token}&i={ID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getResponseInfo(@PathParam("token") String token,
//                                    @PathParam("ID") int ID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Response response = Response.getResponseByID(ID);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            response.setDefaultClass(new DefaultClass(true, token));
//            return response;
//        } catch (Exception ex) {
//            Response response = new Response();
//            response.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return response;
//        }
//    }
//
//    @GET
//    @Path("/cr/t={token}&r={responseID}&t={text}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass commentResponse(@PathParam("token") String token,
//                                        @PathParam("responseID") int responseID,
//                                        @PathParam("text") String text) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            new Comment(responseID, user.getPersonID(), text);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/dc/t={token}&r={commentID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass deleteComment(@PathParam("token") String token,
//                                      @PathParam("commentID") int commentID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            user.setToken(token);
//            user.setLastOnlineDate();
//            Comment.deleteComment(commentID);
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
}
