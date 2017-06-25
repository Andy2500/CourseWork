package ru.hse.coursework.models.User;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.Response.Response;
import ru.hse.coursework.service.DBManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class UserProfile implements Serializable {

    private User user;
    private ArrayList<Response> responses;
    private DefaultClass defaultClass;

    public static UserProfile getUserProfileByID(int ID) throws Exception {
        UserProfile userProfile = new UserProfile();
        userProfile.user = User.getUserByID(ID, true, false, true);
        userProfile.user.clear();
        String query = "Select * From Responses Where PersonID = " + ID;
        userProfile.responses = DBManager.getResponsesByQuery(query);
        return userProfile;
    }

    public static UserProfile getUserProfileByUser(User user) throws Exception {
        UserProfile userProfile = new UserProfile();

        String query = "Select * From Responses Where PersonID = " + user.getPersonID();
        userProfile.user = user;
        userProfile.user.cleanForUser();
        userProfile.responses = DBManager.getResponsesByQuery(query);
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

    public void setResponses(ArrayList<Response> responses) {
        this.responses = responses;
    }
}

