package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Chat.Messages;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/chat")
public class ChatController {

    @POST
    @Path("/md/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeDialog(@FormParam("token") String token,
                                   @FormParam("ID_2") int personID_2) {
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialog getDialog(@FormParam("token") String token,
                            @FormParam("dialogID") int dialogID) {
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass sendMessage(@FormParam("token") String token,
                                    @FormParam("dialogID") int dialogID,
                                    @FormParam("text") String text) {
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Messages getLastMessage(@FormParam("token") String token,
                                   @FormParam("dialogID") int dialogID) {
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
}