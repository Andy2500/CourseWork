package ru.hse.coursework.models.Packages.Offer;

import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class OfferRequest implements Serializable{

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

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public int getPersonID() {
        return personID;
    }

    public int getOfferID() {
        return offerID;
    }

    public int getRequestID() {
        return requestID;
    }

    public User getPerson() {
        return person;
    }

}
