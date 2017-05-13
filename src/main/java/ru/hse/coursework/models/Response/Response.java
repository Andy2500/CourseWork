package ru.hse.coursework.models.Response;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DBManager;
import ru.hse.coursework.service.DateWorker;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Response implements Serializable {

    private int criticID;
    private int personID;
    private int responseID;

    private int mark;
    private String text;
    private Date date;

    private DefaultClass defaultClass;
    private User critic;
    private ArrayList<Comment> comments;

    public Response() {
    }

    public Response(int criticID, int personID, String text, int mark) throws Exception {
        this.criticID = criticID;
        this.personID = personID;
        this.text = text;
        this.mark = mark;
        this.defaultClass = new DefaultClass(true, "");

        String command = "Insert Into Responses (ResponseID, PersonID, CriticID, Text, Mark, Date)" +
                "Values ((Select MAX(ResponseID) FROM Responses) + 1, " + personID + "," + criticID + ",'" + text + "'," + mark + ",'" + DateWorker.getNowMomentInUTC() + "')";
        DBManager.execCommand(command);
    }

    public static Response getResponseByID(int ID) throws Exception {
        String query = "Select * From Responses Where ResponseID = " + ID;
        Response response = DBManager.getResponseByQuery(query);

        response.critic.clear();
        for (Comment comment : response.comments) {
            comment.getCommenter().clear();
        }
        return response;
    }

    public static Response parseResponseFromResultSet(ResultSet resultSet) throws Exception {
        Response response = new Response();
        response.setResponseID(resultSet.getInt("ResponseID"));
        response.setComments(Comment.getCommentsByResponseID(response.getResponseID()));
        response.setPersonID(resultSet.getInt("PersonID"));
        response.setCriticID(resultSet.getInt("CriticID"));
        response.setCritic(User.getUserByID(response.getCriticID()));
        response.setMark(resultSet.getInt("Mark"));
        response.setText(resultSet.getString("Text"));
        response.setDate(resultSet.getTimestamp("Date"));

        response.getCritic().clear();
        return response;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public int getCriticID() {
        return criticID;
    }

    public void setCriticID(int criticID) {
        this.criticID = criticID;
    }

    public int getPersonID() {
        return personID;
    }

    public int getResponseID() {
        return responseID;
    }

    public String getText() {
        return text;
    }

    public int getMark() {
        return mark;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setResponseID(int responseID) {
        this.responseID = responseID;
    }

    public void setCritic(User user) {
        this.critic = user;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCritic() {
        return critic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
