package ru.hse.coursework.models.Response;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Response implements Serializable {

    private Integer criticID;
    private Integer personID;
    private Integer responseID;

    private Integer mark;
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
                "Values ((Select MAX(ResponseID) FROM Responses) + 1, " + personID + "," + criticID + ",'" + text + "'," + mark + ",'" + Service.getNowMomentInUTC() + "')";
        Service.execCommand(command);
    }

    public static Response getResponseByID(int ID) throws Exception {
        String query = "Select * From Responses Where ResponseID = " + ID;
        Response response = Service.getResponseByQuery(query);

        response.critic.clear();
        for (Comment comment : response.comments) {
            comment.getCommenter().clear();
        }
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
