package ru.hse.coursework.models.User;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.service.DBManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Users {
    private ArrayList<User> users;
    private DefaultClass defaultClass;

    public Users() {
    }

    public static Users getUsersForConfirm() throws Exception {
        String query = "Select * From Users Where Status = 0";
        return DBManager.getUsersByQuery(query);
    }

    public static Users getUsersWithLogin(String login) throws Exception {
        String query = "Select * From Users Where Login Like '%" + login + "%'";
        return DBManager.getUsersByQuery(query);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
