package ru.hse.coursework.models.Response;

import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Comment implements Serializable {

    private int CommentID;
    private int ResponseID;
    private int PersonID;
    private String Text;
    private User commenter;
    private Date date;

    public Comment() {
    }

    public Comment(int responseID, int personID, String text) throws Exception {
        ResponseID = responseID;
        PersonID = personID;
        Text = text;

        String command = "Insert Into Comments (CommentID, ResponseID, PersonID, Text, Date)" +
                "Values (Max(CommentID) + 1, " + responseID + ", " + personID + ",'" + text + "','" + Service.getNowMomentInUTC() + "')";
        Service.execCommand(command);
    }

    public static void deleteComment(int commentID) throws Exception {
        String command = "Delete From Comments Where CommentID = " + commentID;
        Service.execCommand(command);
    }

    public static ArrayList<Comment> getCommentsByResponseID(int ID) throws Exception {
        String query = "Select * From Comments Where ResponseID = " + ID;
        return Service.getCommentsByQuery(query);
    }

    public int getCommentID() {
        return CommentID;
    }

    public int getResponseID() {
        return ResponseID;
    }

    public int getPersonID() {
        return PersonID;
    }

    public String getText() {
        return Text;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public void setResponseID(int responseID) {
        ResponseID = responseID;
    }

    public void setPersonID(int personID) {
        PersonID = personID;
    }

    public void setText(String text) {
        Text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
