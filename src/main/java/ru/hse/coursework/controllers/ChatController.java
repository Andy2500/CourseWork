package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/chat")
public class ChatController {

    @GET
    @Path("/md/t={token}&id1={ID_1}&id2={ID_2}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeDialog(@PathParam("token") String token,
                                   @PathParam("ID_1") int PersonID_1,
                                   @PathParam("ID_2") int PersonID_2) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            new Dialog(PersonID_1, PersonID_2);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/gam/t={token}&id1={ID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialog getDialog(@PathParam("token") String token,
                            @PathParam("ID") int DialogID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);
            Dialog dialog = Dialog.getDialogByID(DialogID);
            dialog.setDefaultClass(new DefaultClass(true, token));
            return dialog;
        } catch (Exception ex) {
            Dialog dialog = new Dialog();
            dialog.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return dialog;
        }
    }

    @GET
    @Path("/sm/t={token}&p={PersonID}&d={DialogID}&tx={text}&da={date}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass sendMessage(@PathParam("token") String token,
                                    @PathParam("PersonID") int personID,
                                    @PathParam("DialogID") int dialogID,
                                    @PathParam("text") String text,
                                    @PathParam("date") String date) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            new Message(personID, dialogID, text, Service.dateFromString(date));

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    @GET
    @Path("/glm/t={token}&p={PersonID}&d={DialogID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message getLastMessage(@PathParam("token") String token,
                                  @PathParam("PersonID") int personID,
                                  @PathParam("DialogID") int dialogID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Message message = Message.getLastMessagesByDialogID(dialogID);
            message.setDefaultClass(new DefaultClass(true, token));
            return message;
        } catch (Exception ex) {
            Message message = new Message();
            message.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return message;
        }
    }
}
