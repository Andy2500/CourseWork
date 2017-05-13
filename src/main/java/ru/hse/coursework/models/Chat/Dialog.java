package ru.hse.coursework.models.Chat;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DBManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

@XmlRootElement
public class Dialog implements Serializable {

    private int dialogID;
    private int personID_1;
    private int personID_2;

    private ArrayList<Message> messages;
    private User person;

    private DefaultClass defaultClass;

    public Dialog() {
    }

    public Dialog(int PersonID_1, int PersonID_2) throws Exception {
        this.personID_1 = PersonID_1;
        this.personID_2 = PersonID_2;

        String command = "Insert Into Dialogs (DialogID, PersonID_1, PersonID_2) " +
                "Values ((Select Max(DialogID) From Dialogs) + 1," + PersonID_1 + "," + PersonID_2 + ")";
        DBManager.execCommand(command);
    }

    public static Dialog getDialogByPersonIDs(int personID_1, int personID_2, Boolean type, int personID) throws Exception {
        String query = "Select * From Dialogs Where (PersonID_1 = " + personID_1 + " AND PersonID_2 = " + personID_2 + ") OR (PersonID_2 = " + personID_1 + "AND PersonID_1 = " + personID_2 + ")";
        return DBManager.getDialogByQuery(query, type, personID);
    }

    public static Dialog parseDialogFromResultSet(ResultSet resultSet) throws Exception {
        Dialog dialog = new Dialog();

        dialog.setDialogID(resultSet.getInt("DialogID"));
        dialog.setPersonID_1(resultSet.getInt("PersonID_1"));
        dialog.setPersonID_2(resultSet.getInt("PersonID_2"));

        dialog.setMessages(Message.getMessagesByDialogID(dialog.getDialogID()));
        return dialog;
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

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }
}
