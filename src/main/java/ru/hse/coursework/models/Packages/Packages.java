package ru.hse.coursework.models.Packages;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class Packages implements Serializable {

    private ArrayList<Package> packages;
    private DefaultClass defaultClass;

    public static Packages getPackagesByUserID(int ID) throws Exception {
        String query = "Select * From Packages Where (ProducerID = " + ID + ") OR ( ConsumerID = " + ID + ") OR (GetterID = " + ID + ")";
        return Service.getPackagesByQuery(query);
    }

    public ArrayList<Package> getPackages() {
        return packages;
    }

    public void setPackages(ArrayList<Package> packages) {
        this.packages = packages;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }


}
