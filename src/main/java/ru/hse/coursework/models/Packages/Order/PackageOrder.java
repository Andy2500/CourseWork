package ru.hse.coursework.models.Packages.Order;

import org.codehaus.jackson.node.ObjectNode;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class PackageOrder implements Serializable {

    private int orderID;
    private int personID;

    private String source;
    private String destination;

    private String text;

    private Date startDate;
    private Date endDate;
    private Date publishDate;

    private ArrayList<OrderRequest> requests;
    private User person;
    private DefaultClass defaultClass;

    public PackageOrder() {
    }

    public PackageOrder(int personID, String source, String destination, Date startDate, Date endDate, String text) throws Exception {
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.personID = personID;
        String command = "Insert Into Orders (OrderID, PersonID, Source, Destination, StartDate, EndDate, Text, Photo, PublishDate)" +
                "Values ((Select Max(OrderID) From Orders) + 1,"+ personID +  ",'" + source + "', '" + destination + "',' " + Service.makeSqlDateString(startDate) + "',' " + Service.makeSqlDateString(endDate)+ "',' "+ text + "', NULL,'" + Service.getNowMomentInUTC() +"')";
        Service.execCommand(command);
        command = "Update Users Set CountOfOrders = CountOfOrders + 1 Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public ObjectNode getJSONNode()
    {
        return null;
    }

    public String getJSON()
    {
        return null;
    }

    public static PackageOrder getOrderByID(int ID) throws Exception {
        String query = "Select * From Orders Where OrderID = " + ID;
        return Service.getOrderByQuery(query);
    }

    public static void deletePackageOrder(int ID) throws Exception {
        PackageOrder order = Service.getOrderByQuery("Select * From Orders Where OrderID =" + ID);
        String command = "Delete From Orders Where OrderID = " + ID;
        Service.execCommand(command);
        command = "Update Users Set CountOfOrders = CountOfOrders - 1 Where PersonID = " + order.getPersonID();
        Service.execCommand(command);
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public int getOrderID() {
        return orderID;
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

    public String getText() {
        return text;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRequests(ArrayList<OrderRequest> requests) {
        this.requests = requests;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public User getPerson() {
        return person;
    }

    public ArrayList<OrderRequest> getRequests() {
        return requests;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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
}
