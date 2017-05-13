package ru.hse.coursework.models.Packages;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.service.DBManager;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Packages implements Serializable {

    private ArrayList<Package> packages;
    private DefaultClass defaultClass;

    public static Packages getPackagesByUserID(int ID) throws Exception {
        String query = "Select * From Packages Where (ProducerID = " + ID + ") OR ( ConsumerID = " + ID + ") OR (GetterID = " + ID + ")";
        Packages packages = DBManager.getPackagesByQuery(query);

        for (int i = 0; i < packages.getPackages().size(); i++) {
            if (packages.getPackages().get(i).getEventDate().getTime() + 86400000 < (new Date().getTime()) && packages.getPackages().get(i).getStatus() == 0) {
                Package.deletePackage(packages.getPackages().get(i).getPackageID());
                packages.getPackages().remove(i);
            }
        }
        return packages;
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
