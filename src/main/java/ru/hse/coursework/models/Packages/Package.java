package ru.hse.coursework.models.Packages;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class Package implements Serializable {

    private Integer consumerID;
    private Integer producerID;
    private Integer packageID;
    private Integer status; //0 - подтерждено с двух сторон, 1 - назначена встреча, 2 - подтверждено исполнителем,
    private Float length;

    private String source;
    private String destination;
    private Date event;

    private Date startDate;
    private Date endDate;
    private Date publishDate;

    private String text;

    private User consumer;
    private User producer;
    private DefaultClass defaultClass;

    public Package() {
    }

    public Package(int consumerID, int producerID, String source, String destination, Date startDate, Date endDate, String text, float length, Date event) throws Exception {
        this.consumerID = consumerID;
        this.producerID = producerID;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.length = length;

        String command = "";

        if (event != null) {

            command = "Insert Into Packages (PackageID, ConsumerID, ProducerID, Source, Destination, StartDate, EndDate, Text, PublishDate, Status, Length, Event)"
                    + "Values ((Select Max(PackageID) From Packages) + 1," + consumerID + "," + producerID + ",'" + source + "','" + destination + "','" + startDate + "','" + endDate + "','" + text + "','" + Service.getNowMomentInUTC() + "', 0, " + length + "," + Service.makeSqlDateString(event) + ")";
        } else {
            command = "Insert Into Packages (PackageID, ConsumerID, ProducerID, Source, Destination, StartDate, EndDate, Text, PublishDate, Status, Length)"
                    + "Values ((Select Max(PackageID) From Packages) + 1," + consumerID + "," + producerID + ",'" + source + "','" + destination + "','" + startDate + "','" + endDate + "','" + text + "','" + Service.getNowMomentInUTC() + "', 0, " + length + ")";
        }

        Service.execCommand(command);
    }

    public static Package getPackageByID(int ID) throws Exception {
        String query = "Select * From Packages Where PackageID = " + ID;
        return Service.getPackageByQuery(query);
    }

    public static void setEvent(Date date, int packageID) throws Exception {
        String query = "Update Packages Set Event = " + Service.makeSqlDateString(date) + " Where PackageID = " + packageID;
        Service.execCommand(query);
    }

    public static void setStatus(int status, int packageID) throws Exception {
        String query = "Update Packages Set Status = " + status + " Where PackageID = " + packageID;
        Service.execCommand(query);
    }

    public static void deletePackage(int packageID, int personID, int personID2) throws Exception {
        String command = "Delete From Orders Where OrderID = " + packageID;
        Service.execCommand(command);
        command = "Update Users Set CountOfPackages = CountOfPackages - 1 Where PersonID = " + personID;
        Service.execCommand(command);
        command = "Update Users Set CountOfPackages = CountOfPackages - 1 Where PersonID = " + personID2;
        Service.execCommand(command);
    }

    public void setConsumerID(int consumerID) {
        this.consumerID = consumerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
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

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public int getConsumerID() {
        return consumerID;
    }

    public int getProducerID() {
        return producerID;
    }

    public int getPackageID() {
        return packageID;
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

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public User getConsumer() {
        return consumer;
    }

    public void setConsumer(User consumer) {
        this.consumer = consumer;
    }

    public User getProducer() {
        return producer;
    }

    public void setProducer(User producer) {
        this.producer = producer;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Date getEvent() {
        return event;
    }

    public void setEvent(Date event) {
        this.event = event;
    }
}
