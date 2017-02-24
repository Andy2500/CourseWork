package ru.hse.coursework.models.Packages;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class Package implements Serializable{

    private Integer consumerID;
    private Integer producerID;
    private Integer packageID;

    private String source;
    private String destination;
    private String photo;

    private Date startDate;
    private Date endDate;
    private Date publishDate;

    private String text;

    private User consumer;
    private User producer;
    private DefaultClass defaultClass;

    public Package(){
    }
    public Package(int consumerID, int producerID, String source, String destination, Date startDate, Date endDate, String text) throws Exception {
        this.consumerID = consumerID;
        this.producerID = producerID;
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;

        String command = "Insert Into Packages (PackageID, ConsumerID, ProducerID, Source, Destination, StartDate, EndDate, Text, PublishDate)"
                + "Values ((Select Max(PackageID) From Packages) + 1," + consumerID + "," + producerID + ",'" + source + "','" + destination + "','" + startDate + "','" + endDate +"','" + text + "','" +Service.getNowMomentInUTC() +"')";
        Service.execCommand(command);
    }

    public static Package getPackageByID(int ID) throws Exception {
        String query = "Select * From Packages Where PackageID = " + ID;
        return Service.getPackageByQuery(query);
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
