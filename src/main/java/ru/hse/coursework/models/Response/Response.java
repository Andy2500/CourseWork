package ru.hse.coursework.models.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
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
                "Values ((Select MAX(ResponseID) FROM Responses) + 1, " + personID + "," + criticID + ",'" + text + "'," + mark + ",'" + Service.getNowMomentInUTC() + "')";
        Service.execCommand(command);
    }

    public String getJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode Node = mapper.createObjectNode();
        Node.put("criticID", criticID);
        Node.put("personID", personID);
        Node.put("mark", mark);
        Node.put("text", text);
        Node.put("date", date.toString());

        String criticjson = "";
        if (critic != null) {
            criticjson = critic.getJSON();
        }

        String commentjson = "{";
        if (comments != null) {
            for (int i = 0; i < comments.size(); i++) {
                commentjson = commentjson + "\"comment_" + i + "\":" + comments.get(i).getJSON() + "},";
            }

            commentjson = commentjson.substring(0, commentjson.length() - 2);
        }

        commentjson += "}";

        String nodemapper;
        String nodestring = "";
        try {
            nodemapper = mapper.writeValueAsString(Node);
            nodestring = nodemapper.substring(1, nodemapper.length() - 1);
        } catch (Exception ex) {
        }

        String resultjson = "{" + nodestring + ", \"critic\":" + criticjson + ",\"comments\":" + commentjson + "}";
        return resultjson;
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

    @Override
    public String toString() {
        return "{\"response\":" + this.getJSON() + ", \"defaultClass\": " + defaultClass.getJSON() + "}";
    }
}
