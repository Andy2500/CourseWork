package ru.hse.coursework.models.Packages.Order;

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
public class PackageOrder implements Serializable {

    private int orderID;
    private int personID;

    private String source;
    private String destination;

    private String text;

    private Date startDate;
    private Date endDate;
    private Date publishDate;

    private Integer watches;

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
        this.watches = 0;

        String command = "Insert Into Orders (OrderID, PersonID, Source, Destination, StartDate, EndDate, Text, PublishDate, Watches)" +
                "Values ((Select Max(OrderID) From Orders) + 1," + personID + ",'" + source + "', '" + destination + "',' " + DateWorker.makeSqlDateString(startDate) + "',' " + DateWorker.makeSqlDateString(endDate) + "',' " + text + "','" + DateWorker.getNowMomentInUTC() + "', 0)";
        DBManager.execCommand(command);
    }

    public static PackageOrder getOrderByID(int ID) throws Exception {
        String query = "Update Orders Set Watches = Watches + 1 Where OrderID = " + ID;
        DBManager.execCommand(query);
        query = "Select * From Orders Where OrderID = " + ID;
        return DBManager.getOrderByQuery(query);
    }

    public static void deletePackageOrder(int packageID, int personID) throws Exception {
        String command = "Delete From Orders Where OrderID = " + packageID;
        DBManager.execCommand(command);
        command = "Delete From OrderRequests Where OrderID = " + packageID;
        DBManager.execCommand(command);
    }

    public static Orders getOrdersByRequests(ArrayList<OrderRequest> requests) throws Exception {
        String query = "Select * From Orders Where ";

        for (int i = 0; i < requests.size(); i++) {
            if (i == requests.size() - 1) {
                query += "OrderID = " + requests.get(i).getOrderID();
            } else {
                query += "OrderID = " + requests.get(i).getOrderID() + "OR ";
            }
        }

        return DBManager.getOrdersByQuery(query);
    }

    public static PackageOrder parseOrderFromResultSet(ResultSet resultSet) throws Exception {
        PackageOrder order = new PackageOrder();

        order.setOrderID(resultSet.getInt("OrderID"));
        order.setText(resultSet.getString("Text"));
        order.setPersonID(resultSet.getInt("PersonID"));
        order.setStartDate(resultSet.getTimestamp("StartDate"));
        order.setEndDate(resultSet.getTimestamp("EndDate"));
        order.setDestination(resultSet.getString("Destination"));
        order.setSource(resultSet.getString("Source"));
        order.setPublishDate(resultSet.getTimestamp("PublishDate"));
        order.setWatches(resultSet.getInt("Watches"));

        order.setRequests(OrderRequest.getRequestsByOrderID(order.getOrderID()));
        order.setPerson(User.getUserByID(order.getPersonID(), false, false, true));

        order.getPerson().clear();
        return order;
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

    public Integer getWatches() {
        return watches;
    }

    public void setWatches(Integer watches) {
        this.watches = watches;
    }


}
