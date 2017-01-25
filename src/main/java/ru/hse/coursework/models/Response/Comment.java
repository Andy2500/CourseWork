package ru.hse.coursework.models.Response;

import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Comment implements Serializable {

    private Integer commentID;
    private Integer responseID;
    private Integer personID;
    private String text;
    private Date date;

    private User commenter;

    public Comment() {
    }

    public Comment(int responseID, int personID, String text) throws Exception {
        this.responseID = responseID;
        this.personID = personID;
        this.text = text;

        String command = "Insert Into Comments (CommentID, ResponseID, PersonID, Text, Date)" +
                "Values ((Select MAX(CommentID) FROM Comments) + 1, " + responseID + ", " + personID + ",'" + text + "','" + Service.getNowMomentInUTC() + "')";
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
        return commentID;
    }

    public int getResponseID() {
        return responseID;
    }

    public int getPersonID() {
        return personID;
    }

    public String getText() {
        return text;
    }

    public User getCommenter() {
        return commenter;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
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
}
