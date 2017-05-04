package ru.hse.coursework.models.Packages.Offer;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Offers implements Serializable {

    private ArrayList<PackageOffer> offers;
    private DefaultClass defaultClass;

    public static Offers getOffersByParams(String source, String destination, Date startDate, Date endDate) throws Exception {
        String query = "Select * From Offers Where (Source = '" + source + "') AND (Destination = '" + destination + "') AND (StartDate = '" + Service.makeSqlDateString(startDate) + " 00:00') AND (EndDate <= '" + Service.makeSqlDateString(endDate) + " 23:59')";
        return Service.getOffersByQuery(query);
    }

    public static Offers getOffersByUserID(int ID) throws Exception {
        String query = "Select * From Offers Where PersonID = " + ID;
        return Service.getOffersByQuery(query);
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public void setOffers(ArrayList<PackageOffer> offers) {
        this.offers = offers;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public ArrayList<PackageOffer> getOffers() {
        return offers;
    }


}
