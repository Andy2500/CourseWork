package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Dialogs;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/chat")
public class ChatController {

    /**
     * Метод для получения диалога между пользователями
     * Путь:  /chat/gd/
     *
     * @param token    - токен пользователя
     * @param personID - ID пользователя
     * @return сущность Dialog и DefaultClass
     */
    @POST
    @Path("/gd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialog getDialog(@FormParam("token") String token,
                            @FormParam("personID") int personID) {
        try {
            User user = User.getUserByToken(token);
            Dialog dialog = Dialog.getDialogByPersonIDs(user.getPersonID(), personID, false, 0);
            if (dialog.getDialogID() == 0) {
                new Dialog(user.getPersonID(), personID);
                dialog = Dialog.getDialogByPersonIDs(user.getPersonID(), personID, false, 0);
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

    /**
     * Метод для отправки сообщения в диалог
     * Путь:  /chat/sm/
     *
     * @param token    - токен пользователя
     * @param dialogID - ID диалога
     * @param text     - текст
     * @return DefaultClass
     */
    @POST
    @Path("/sm/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass sendMessage(@FormParam("token") String token,
                                    @FormParam("dialogID") int dialogID,
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

    /**
     * Метод для получения диалогов пользователя
     * Путь:  /chat/guds/
     *
     * @param token токен пользователя
     * @return массив сущностей Dialog и Default Class
     */
    @POST
    @Path("/guds/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Dialogs getUserDialogs(@FormParam("token") String token) {
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