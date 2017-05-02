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
    @Path("/gd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialog getDialog(@FormParam("token") String token,
                            @FormParam("personID") int id,
                            @FormParam("date") String date,
                            @FormParam("ID_2") int personID_2) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Dialog dialog = Dialog.getDialogByPersonIDs(id, personID_2);

                if (dialog.getDialogID() == 0) {
                    new Dialog(id, personID_2);
                    dialog = Dialog.getDialogByPersonIDs(id, personID_2);
                }

                dialog.setMessages(Message.getMessagesByDialogID(dialog.getDialogID()));

                dialog.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
                return dialog;
            }

            throw new Exception("token error");
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
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("text") String text) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                new Message(user.getPersonID(), dialogID, text);

                User.setLastOnlineDate(id);

                return new DefaultClass(true, token);
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    @POST
    @Path("/glm/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Messages getLastMessages(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("dialogID") int dialogID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Messages messages = Messages.getLastMessagesByDialogID(dialogID);
                messages.setDefaultClass(new DefaultClass(true, token));

                User.setLastOnlineDate(id);
                return messages;
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            Messages messages = new Messages();
            messages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return messages;
        }
    }
}