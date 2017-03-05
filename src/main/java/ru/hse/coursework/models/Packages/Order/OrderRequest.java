package ru.hse.coursework.models.Packages.Order;

import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement
public class OrderRequest implements Serializable {

    private int requestID;
    private int personID;
    private int orderID;


    private User person;

    public OrderRequest() {
    }

    public OrderRequest(int personID, int orderID) throws Exception {
        this.personID = personID;
        this.orderID = orderID;

        String command = "Insert Into OrderRequests (RequestID, PersonID, OrderID) Values ((Select Max(RequestID) From OrderRequests) + 1," + personID + "," + orderID + ")";
        Service.execCommand(command);
    }

    public static void deleteRequest(int requestID) throws Exception {
        String command = "Delete From OrderRequests Where RequestID = " + requestID;
        Service.execCommand(command);
    }

    public static OrderRequest getRequestByID(int ID) throws Exception {
        String query = "Select * From OrderRequests Where RequestID = " + ID;
        return Service.getOrderRequestByQuery(query);
    }

    public static ArrayList<OrderRequest> getRequestsByOrderID(int ID) throws Exception {
        String query = "Select * From OrderRequests Where OrderID = " + ID;
        return Service.getOrderRequestsByQuery(query);
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getPersonID() {
        return personID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public int getRequestID() {
        return requestID;
    }

    public User getPerson() {
        return person;
    }

}
