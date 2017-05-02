package ru.hse.coursework.models.Packages.Offer;

import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class OfferRequest implements Serializable {

    private Integer personID;
    private Integer offerID;
    private Integer requestID;

    private User person;

    public OfferRequest() {
    }

    public OfferRequest(int personID, int offerID) throws Exception {
        this.personID = personID;
        this.offerID = offerID;

        String command = "Insert Into OfferRequests (RequestID, PersonID, OfferID) Values ((Select Max(RequestID) From OfferRequests) + 1," + personID + "," + offerID + ")";
        Service.execCommand(command);
    }

    public static OfferRequest getRequestByID(int ID) throws Exception {
        String query = "Select * From OfferRequests Where RequestID = " + ID;
        return Service.getOfferRequestByQuery(query);
    }

    public static void deleteRequest(int requestID) throws Exception {
        String command = "Delete From OfferRequests Where RequestID = " + requestID;
        Service.execCommand(command);
    }

    public static ArrayList<OfferRequest> getRequestsByOfferID(int ID) throws Exception {
        String query = "Select * From OfferRequests Where OfferID = " + ID;
        return Service.getOfferRequestsByQuery(query);
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public Integer getOfferID() {
        return offerID;
    }

    public void setOfferID(Integer offerID) {
        this.offerID = offerID;
    }

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }
}
