package ru.hse.coursework.models.Chat;

import org.codehaus.jackson.node.ObjectNode;
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

        String command = "Insert Into Dialogs (DialogID, PersonID_1, PersonID_2) Values ((Select Max(DialogID) From Dialogs) + 1," + PersonID_1 + "," + PersonID_2 + ")";
        Service.execCommand(command);
    }

    public ObjectNode getJSONNode()
    {
        return null;
    }

    public String getJSON()
    {
        return null;
    }

    public static Dialog getDialogByID(int ID) throws Exception {

        String query = "Select * From Dialogs Where DialogID = " + ID;
        return Service.getDialogByQuery(query);
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public void setPersonID_1(int personID_1) {
        this.personID_1 = personID_1;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setPersonID_2(int personID_2) {
        this.personID_2 = personID_2;
    }

    public void setDialogID(int dialogID) {
        this.dialogID = dialogID;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public int getDialogID() {
        return dialogID;
    }

    public int getPersonID_1() {
        return personID_1;
    }

    public int getPersonID_2() {
        return personID_2;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }


}
