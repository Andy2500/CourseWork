package ru.hse.coursework.models.Chat;

import ru.hse.coursework.models.Service.DefaultClass;

import java.util.ArrayList;

public class Messages {
    private ArrayList<Message> messages;
    private DefaultClass defaultClass;

    public static Messages getLastMessagesByDialogID(int dialogID) throws Exception {
        Messages messages = new Messages();
        messages.setMessages(Message.getLastMessagesByDialogID(dialogID));
        return messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
