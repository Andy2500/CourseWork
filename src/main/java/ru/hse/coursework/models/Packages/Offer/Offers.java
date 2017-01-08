package ru.hse.coursework.models.Packages.Offer;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class Offers implements Serializable {

    private ArrayList<PackageOffer> offers;
    private DefaultClass defaultClass;

    public static Offers getOffersByParams(String source, String destination, String startDate, String endDate) throws Exception {
        String query = "Select * From Offers Where (Source = ' " + source + "') AND (Destination = '" + destination + "') AND (Date > '" + startDate + "') AND (Date < '" + endDate + "')";
        return Service.getOffersByQuery(query);
    }

    public static Offers getOffersByID(int ID) throws Exception {
        String query = "Select * From Offers Where OfferID = " + ID;
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
