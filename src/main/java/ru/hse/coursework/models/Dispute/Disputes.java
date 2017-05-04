package ru.hse.coursework.models.Dispute;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Disputes {
    private ArrayList<Dispute> disputes;
    private DefaultClass defaultClass;

    public Disputes() {
    }

    public Disputes(ArrayList<Dispute> disputes, DefaultClass defaultClass) {
        this.disputes = disputes;
        this.defaultClass = defaultClass;
    }

    public static Disputes getDisputesByPersonID(int personID) throws Exception {
        String query = "Select * From Disputes Where PersonID = " + personID;
        Disputes disputes = new Disputes();
        disputes.setDisputes(Service.getDisputesByQuery(query));
        return disputes;
    }

    public static Disputes getAllDisputes() throws Exception {
        String query = "Select * From Disputes Where Status = 0";
        Disputes disputes = new Disputes();
        disputes.setDisputes(Service.getDisputesByQuery(query));
        return disputes;
    }

    public ArrayList<Dispute> getDisputes() {
        return disputes;
    }

    public void setDisputes(ArrayList<Dispute> disputes) {
        this.disputes = disputes;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
