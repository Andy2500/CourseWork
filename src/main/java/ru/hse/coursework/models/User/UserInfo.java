package ru.hse.coursework.models.User;

import ru.hse.coursework.models.Service.DefaultClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class UserInfo implements Serializable {
    private int personID;
    private DefaultClass defaultClass;

    public UserInfo() {

    }

    public UserInfo(int personID, DefaultClass defaultClass) {
        this.personID = personID;
        this.defaultClass = defaultClass;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }


}
