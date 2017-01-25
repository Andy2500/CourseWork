package ru.hse.coursework.models.User;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import ru.hse.coursework.models.Response.Response;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class UserProfile implements Serializable {

    private User user;
    private ArrayList<Response> responses;
    private DefaultClass defaultClass;

    public String getJson(){

        ObjectMapper mapper = new ObjectMapper();

        String defjson = defaultClass.getJSON();
        String userjson = "";
        if(user != null)
        {
            userjson = user.getJSON();
        }

        String responsejson = "{";

        if(responses != null)
        {
            for(int i = 0; i < responses.size(); i++)
            {
                responsejson = responsejson + "\"response_" + i + "\":" + responses.get(i).getJSON() + "},";
            }
            responsejson = responsejson.substring(0, responsejson.length() - 2);
        }
        responsejson += "}";

        String resultjson = "{ \"defaultClass\":"+defjson +
                            ",\"user\":" + userjson +
                            ",\"responses\":"+ responsejson + "}";
        return resultjson;
    }

    public static UserProfile getUserProfileByID(int ID) throws Exception {
        UserProfile userProfile = new UserProfile();
        userProfile.user = User.getUserByID(ID);
        userProfile.user.clear();
        String query = "Select * From Responses Where PersonID = " + ID;
        userProfile.responses = Service.getResponsesByQuery(query);
        return userProfile;
    }

    public static UserProfile getUserProfileByUser(User user) throws Exception {
        UserProfile userProfile = new UserProfile();

        String query = "Select * From Responses Where PersonID = " + user.getPersonID();
        userProfile.user = user;
        userProfile.user.cleanForUser();
        userProfile.responses = Service.getResponsesByQuery(query);
        return userProfile;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Response> getResponses() {
        return responses;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}

