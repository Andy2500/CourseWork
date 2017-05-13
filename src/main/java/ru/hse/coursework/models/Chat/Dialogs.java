package ru.hse.coursework.models.Chat;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.service.DBManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class Dialogs implements Serializable {

    private ArrayList<Dialog> dialogs;
    private DefaultClass defaultClass;

    public Dialogs() {

    }

    public Dialogs(ArrayList<Dialog> dialogs, DefaultClass defaultClass) {
        this.dialogs = dialogs;
        this.defaultClass = defaultClass;
    }

    public static Dialogs getDialogsByPersonID(int personID) throws Exception {
        String query = "Select * From Dialogs Where PersonID_1 = " + personID + " OR PersonID_2 = " + personID;
        return new Dialogs(DBManager.getDialogsByQuery(query, personID), new DefaultClass(true, ""));
    }

    public ArrayList<Dialog> getDialogs() {
        return dialogs;
    }

    public void setDialogs(ArrayList<Dialog> dialogs) {
        this.dialogs = dialogs;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
