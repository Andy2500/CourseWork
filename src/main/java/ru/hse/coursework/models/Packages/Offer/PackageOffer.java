package ru.hse.coursework.models.Packages.Offer;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DBManager;
import ru.hse.coursework.service.DateWorker;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class PackageOffer implements Serializable {
    private Integer offerID;
    private Integer personID;

    private String source;
    private String destination;

    private String text;

    private Date startDate;
    private Date endDate;
    private Date publishDate;

    private Integer watches;

    private ArrayList<OfferRequest> requests;
    private DefaultClass defaultClass;
    private User person;

    public PackageOffer() {
    }

    public PackageOffer(int personID, String source, String destination, Date startDate, Date endDate, String text) throws Exception {
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.personID = personID;
        this.watches = 0;

        String command = "Insert Into Offers (OfferID, PersonID, Source, Destination, StartDate, EndDate, Text, PublishDate, Watches)" +
                "Values ((Select Max(OfferID) From Offers) + 1, " + personID + ",'" + source + "', '" + destination + "','" + DateWorker.makeSqlDateString(startDate) + "','" + DateWorker.makeSqlDateString(endDate) + "','" + text + "','" + DateWorker.getNowMomentInUTC() + "', 0 )";
        DBManager.execCommand(command);
    }

    public static PackageOffer getOfferByID(int ID) throws Exception {
        String query = "Select * From Offers Where OfferID = " + ID;
        return DBManager.getOfferByQuery(query);
    }

    public static void deletePackageOffer(int ID, int personID) throws Exception {
        String command = "Delete From Offers Where OfferID = " + ID;
        DBManager.execCommand(command);
        command = "Delete From OfferRequests Where OfferID = " + ID;
        DBManager.execCommand(command);
    }

    public static Offers getOffersByRequests(ArrayList<OfferRequest> requests) throws Exception {
        String query = "Select * From Offers Where ";

        for (int i = 0; i < requests.size(); i++) {
            if (i == requests.size() - 1) {
                query += "OfferID = " + requests.get(i).getOfferID().toString();
            } else {
                query += "OfferID = " + requests.get(i).getOfferID().toString() + "OR ";
            }
        }

        return DBManager.getOffersByQuery(query);
    }

    public static PackageOffer parseOfferFromResultSet(ResultSet resultSet) throws Exception {
        PackageOffer offer = new PackageOffer();

        offer.setOfferID(resultSet.getInt("OfferID"));
        offer.setText(resultSet.getString("Text"));
        offer.setPersonID(resultSet.getInt("PersonID"));
        offer.setStartDate(resultSet.getTimestamp("StartDate"));
        offer.setEndDate(resultSet.getTimestamp("EndDate"));
        offer.setDestination(resultSet.getString("Destination"));
        offer.setSource(resultSet.getString("Source"));
        offer.setPublishDate(resultSet.getTimestamp("PublishDate"));
        offer.setWatches(resultSet.getInt("Watches") + 1);

        offer.setRequests(OfferRequest.getRequestsByOfferID(offer.getOfferID()));
        offer.setPerson(User.getUserByID(offer.getPersonID(), false, false, true));

        offer.getPerson().clear();
        return offer;
    }

    public Integer getOfferID() {
        return offerID;
    }

    public void setOfferID(Integer offerID) {
        this.offerID = offerID;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getWatches() {
        return watches;
    }

    public void setWatches(Integer watches) {
        this.watches = watches;
    }

    public ArrayList<OfferRequest> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<OfferRequest> requests) {
        this.requests = requests;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }
}
