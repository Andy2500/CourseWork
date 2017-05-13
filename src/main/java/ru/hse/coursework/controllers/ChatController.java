package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Dialogs;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Chat.Messages;
import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/chat")
public class ChatController {

    @POST
    @Path("/gd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialog getDialog(@FormParam("token") String token,
                            @FormParam("ID_2") int personID_2) {
        try {
            User user = User.getUserByToken(token);
            Dialog dialog = Dialog.getDialogByPersonIDs(user.getPersonID(), personID_2, false, 0);
            if (dialog.getDialogID() == 0) {
                new Dialog(user.getPersonID(), personID_2);
                dialog = Dialog.getDialogByPersonIDs(user.getPersonID(), personID_2, false, 0);
            }
            dialog.setMessages(Message.getMessagesByDialogID(dialog.getDialogID()));
            dialog.setDefaultClass(new DefaultClass(true, ""));
            User.setLastOnlineDate(user.getPersonID());
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
                                    @FormParam("date") String date,
                                    @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            new Message(user.getPersonID(), dialogID, text);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    @POST
    @Path("/glm/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Messages getLastMessages(@FormParam("token") String token,

                                    @FormParam("date") String date,
                                    @FormParam("dialogID") int dialogID) {
        try {
            User user = User.getUserByToken(token);

            Messages messages = Messages.getLastMessagesByDialogID(dialogID);
            messages.setDefaultClass(new DefaultClass(true, token));

            User.setLastOnlineDate(user.getPersonID());
            return messages;
        } catch (Exception ex) {
            Messages messages = new Messages();
            messages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return messages;
        }
    }

    @POST
    @Path("/guds/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialogs getUserDialogs(@FormParam("token") String token,

                                  @FormParam("date") String date) {
        try {
            User user = User.getUserByToken(token);
            Dialogs dialogs = Dialogs.getDialogsByPersonID(user.getPersonID());
            dialogs.setDefaultClass(new DefaultClass(true, ""));
            User.setLastOnlineDate(user.getPersonID());
            return dialogs;
        } catch (Exception ex) {
            return new Dialogs(new ArrayList<Dialog>(), new DefaultClass(false, ex.getMessage()));
        }
    }
}