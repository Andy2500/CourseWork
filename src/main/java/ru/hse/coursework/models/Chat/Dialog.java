package ru.hse.coursework.models.Chat;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class Dialog implements Serializable {

    private int dialogID;
    private int personID_1;
    private int personID_2;

    private ArrayList<Message> messages;

    private DefaultClass defaultClass;

    public Dialog() {
    }

    public Dialog(int PersonID_1, int PersonID_2) throws Exception {
        this.personID_1 = PersonID_1;
        this.personID_2 = PersonID_2;

        String command = "Insert Into Dialogs (DialogID, PersonID_1, PersonID_2) " +
                "Values ((Select Max(DialogID) From Dialogs) + 1," + PersonID_1 + "," + PersonID_2 + ")";
        Service.execCommand(command);
    }

    public static Dialog getDialogByPersonIDs(int personID_1, int personID_2) throws Exception {
        String query = "Select * From Dialogs Where (PersonID_1 = " + personID_1 + " AND PersonID_2 = " + personID_2 + ") OR (PersonID_2 = " + personID_1 + "AND PersonID_1 = " + personID_2 + ")";
        return Service.getDialogByQuery(query);
    }

    public int getDialogID() {
        return dialogID;
    }

    public void setDialogID(int dialogID) {
        this.dialogID = dialogID;
    }

    public int getPersonID_1() {
        return personID_1;
    }

    public void setPersonID_1(int personID_1) {
        this.personID_1 = personID_1;
    }

    public int getPersonID_2() {
        return personID_2;
    }

    public void setPersonID_2(int personID_2) {
        this.personID_2 = personID_2;
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
