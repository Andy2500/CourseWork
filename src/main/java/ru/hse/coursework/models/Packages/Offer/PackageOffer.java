package ru.hse.coursework.models.Packages.Offer;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class PackageOffer implements Serializable {
    private int offerID;
    private int personID;

    private String source;
    private String destination;
    private String date;
    private String text;
    private Date publishDate;

    private ArrayList<OfferRequest> requests;
    private DefaultClass defaultClass;
    private User person;

    public PackageOffer() {
    }

    public PackageOffer(int personID, String source, String destination, String date, String text) throws Exception {
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.text = text;
        this.personID = personID;
        String command = "Insert Into Offers (OfferID, Source, Destination, Date, Text, Photo, PersonID)" +
                "Values ((Select Max(OfferID) From Offers) + 1, '" + source + "', '" + destination + "',' " + Service.getNowMomentInUTC() + "','" + text + ")";
        Service.execCommand(command);
        command = "Update Users Set CountOfOffers = CountOfOffers + 1 Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public static PackageOffer getOfferByID(int ID) throws Exception {
        String query = "Select * From Offers Where OfferID = " + ID;
        return Service.getOfferByQuery(query);
    }

    public static void deletePackageOffer(int ID) throws Exception {
        PackageOffer offer = Service.getOfferByQuery("Select * From Offers Wher OfferID =" + ID);
        String command = "Delete From Offers Where OfferID = " + ID;
        Service.execCommand(command);
        command = "Update Users Set CountOfOffers = CountOfOffers - 1 Where PersonID = " + offer.getPersonID();
        Service.execCommand(command);
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public void setRequests(ArrayList<OfferRequest> requests) {
        this.requests = requests;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public int getOfferID() {
        return offerID;
    }

    public int getPersonID() {
        return personID;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public ArrayList<OfferRequest> getRequests() {
        return requests;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
