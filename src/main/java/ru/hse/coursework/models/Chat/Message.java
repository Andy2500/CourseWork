package ru.hse.coursework.models.Chat;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Message implements Serializable {

    private Integer messageID;
    private Integer personID;
    private Integer dialogID;
    private String text;
    private Date date;
    private int watched; //1 - просмотрено, 0 - не просмотренно

    private DefaultClass defaultClass;

    public Message() {
    }

    public Message(int personID, int dialogID, String text) throws Exception {
        this.personID = personID;
        this.dialogID = dialogID;
        this.text = text;
        String command = "Insert Into Messages (MessageID, DialogID, PersonID, Text, Date, Watched)"
                + "Values ((Select Max(MessageID) From Messages) + 1 ," + dialogID + "," + personID + ",'" + text + "','" + Service.getNowMomentInUTC() + "', 0 )";
        Service.execCommand(command);
    }

    public static ArrayList<Message> getLastMessagesByDialogID(int dialogID) throws Exception {
        String query = "Select * From Messages Where Watched = 0 AND DialogID = " + dialogID + "";
        return Service.getMessagesByQuery(query);
    }

    public static ArrayList<Message> getMessagesByDialogID(int ID) throws Exception {
        String query = "Select * From Messages Where DialogID =" + ID;
        return Service.getMessagesByQuery(query);
    }


    public Integer getMessageID() {
        return messageID;
    }

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public Integer getDialogID() {
        return dialogID;
    }

    public void setDialogID(Integer dialogID) {
        this.dialogID = dialogID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
