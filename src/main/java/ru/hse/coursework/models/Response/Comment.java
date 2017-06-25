package ru.hse.coursework.models.Response;

import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DBManager;
import ru.hse.coursework.service.DateWorker;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.ResultSet;
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
                "Values ((Select MAX(CommentID) FROM Comments) + 1, " + responseID + ", " + personID + ",'" + text + "','" + DateWorker.getNowMomentInUTC() + "')";
        DBManager.execCommand(command);
    }

    public static void deleteComment(int commentID) throws Exception {
        String command = "Delete From Comments Where CommentID = " + commentID;
        DBManager.execCommand(command);
    }

    public static ArrayList<Comment> getCommentsByResponseID(int ID) throws Exception {
        String query = "Select * From Comments Where ResponseID = " + ID;
        return DBManager.getCommentsByQuery(query);
    }

    public static Comment parseCommentFromResultSet(ResultSet resultSet) throws Exception {
        Comment comment = new Comment();

        comment.setPersonID(resultSet.getInt("PersonID"));
        comment.setText(resultSet.getString("Text"));
        comment.setCommentID(resultSet.getInt("CommentID"));
        comment.setResponseID(resultSet.getInt("ResponseID"));
        comment.setCommenter(User.getUserByID(comment.getPersonID(), false, false, true));
        comment.setDate(resultSet.getTimestamp("Date"));

        comment.getCommenter().clear();
        return comment;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Integer getResponseID() {
        return responseID;
    }

    public void setResponseID(Integer responseID) {
        this.responseID = responseID;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
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

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }
}
