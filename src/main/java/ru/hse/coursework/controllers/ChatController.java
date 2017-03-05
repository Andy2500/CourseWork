package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Chat.Messages;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/chat")
public class ChatController {

    @POST
    @Path("/md/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeDialog(@HeaderParam("token") String token,
                                   @HeaderParam("ID_2") int personID_2) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            new Dialog(user.getPersonID(), personID_2);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gam/")
    @Produces(MediaType.APPLICATION_JSON)
    public Dialog getDialog(@HeaderParam("token") String token,
                            @HeaderParam("dialogID") int dialogID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Dialog dialog = Dialog.getDialogByID(dialogID);
            dialog.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return dialog;
        } catch (Exception ex) {
            Dialog dialog = new Dialog();
            dialog.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return dialog;
        }
    }

    @POST
    @Path("/sm/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass sendMessage(@HeaderParam("token") String token,
                                    @HeaderParam("dialogID") int dialogID,
                                    @HeaderParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            new Message(user.getPersonID(), dialogID, text);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    @POST
    @Path("/glm/")
    @Produces(MediaType.APPLICATION_JSON)
    public Messages getLastMessage(@HeaderParam("token") String token,
                                   @HeaderParam("dialogID") int dialogID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Messages messages = Messages.getLastMessagesByDialogID(dialogID);
            messages.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return messages;
        } catch (Exception ex) {
            Messages messages = new Messages();
            messages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return messages;
        }
    }

    //    @GET
//    @Path("/md/t={token}&id1={ID_1}&id2={ID_2}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_XML)
//    public DefaultClass makeDialog(@PathParam("token") String token,
//                                   @PathParam("ID_1") int PersonID_1,
//                                   @PathParam("ID_2") int PersonID_2) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            new Dialog(user.getPersonID(), PersonID_2);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/gam/t={token}&id1={ID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_XML)
//    public Dialog getDialog(@PathParam("token") String token,
//                            @PathParam("ID") int DialogID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            Dialog dialog = Dialog.getDialogByID(DialogID);
//            dialog.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return dialog;
//        } catch (Exception ex) {
//            Dialog dialog = new Dialog();
//            dialog.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return dialog;
//        }
//    }
//
//    @GET
//    @Path("/sm/t={token}&p={PersonID}&d={DialogID}&tx={text}&da={date}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass sendMessage(@PathParam("token") String token,
//                                    @PathParam("DialogID") int dialogID,
//                                    @PathParam("text") String text) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            new Message(user.getPersonID(), dialogID, text);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//
//    @GET
//    @Path("/glm/t={token}&p={PersonID}&d={DialogID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Message getLastMessage(@PathParam("token") String token,
//                                  @PathParam("DialogID") int dialogID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            Message message = Message.getLastMessagesByDialogID(dialogID);
//            message.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return message;
//        } catch (Exception ex) {
//            Message message = new Message();
//            message.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return message;
//        }
//    }
}
