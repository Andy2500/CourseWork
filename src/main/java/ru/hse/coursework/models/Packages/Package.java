package ru.hse.coursework.models.Packages;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.Packages.Offer.PackageOffer;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DBManager;
import ru.hse.coursework.service.DateWorker;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

@XmlRootElement
public class Package implements Serializable {

    private int consumerID;
    private int producerID;
    private int getterID;
    private int packageID;
    private int status; //-1 - не настроен, 0 - ожидание встречи, 1 - посылка передана, 2 - сделка выполнена исполнителем, 3 - сделка закрыта получателем

    private float sourceLatitude;
    private float sourceLongitude;
    private float destinationLatitude;
    private float destinationLongitude;

    private String sourceAddress;
    private String destinationAddress;

    private Date eventDate;
    private Date finishDate;

    private User consumer;
    private User producer;
    private User getter;

    private String transferProofPhoto;
    private String deliveryProofPhoto;

    private DefaultClass defaultClass;

    public Package(int consumerID, int producerID, int getterID, float sourceLatitude, float sourceLongitude, float destinationLatitude, float destinationLongitude, String sourceAdress, String destinationAdress, Date eventDate, Date finishDate) throws Exception {
        this.consumerID = consumerID;
        this.producerID = producerID;
        this.getterID = getterID;
        this.sourceLatitude = sourceLatitude;
        this.sourceLongitude = sourceLongitude;
        this.destinationLatitude = destinationLatitude;
        this.destinationLongitude = destinationLongitude;
        this.sourceAddress = sourceAdress;
        this.destinationAddress = destinationAdress;
        this.eventDate = eventDate;
        this.finishDate = finishDate;

        String command = "Insert Into Packages (PackageID, ConsumerID, ProducerID, SourceAddress, DestinationAddress, EventDate, FinishDate, Status, GetterID, SourceLatitude, SourceLongitude, DestinationLatitude, DestinationLongitude, TransferProofPhoto, DeliveryProofPhoto)" +
                "Values ((Select Max(PackageID) From Packages) + 1," + this.consumerID + "," + this.producerID + ",'" + this.sourceAddress + "','" + this.destinationAddress + "','" + DateWorker.makeSqlDateString(this.eventDate) + "','" + DateWorker.makeSqlDateString(this.finishDate) + "', 0, " + this.getterID + "," + this.sourceLatitude + ", " + this.sourceLongitude + ", " + this.destinationLatitude + ", " + this.destinationLongitude + ", NULL, NULL)";
        DBManager.execCommand(command);
    }

    public Package(PackageOffer offer, int id) throws Exception {

        String command = "Insert Into Packages (PackageID, ConsumerID, ProducerID, SourceAddress, DestinationAddress, EventDate, FinishDate, Status, GetterID, SourceLatitude, SourceLongitude, DestinationLatitude, DestinationLongitude, TransferProofPhoto, DeliveryProofPhoto)" +
                "Values ((Select Max(PackageID) From Packages) + 1," + offer.getPersonID() + "," + id + ",'" + offer.getSource() + "','" + offer.getDestination() + "','" + DateWorker.makeSqlDateString(offer.getStartDate()) + "','" + DateWorker.makeSqlDateString(offer.getEndDate()) + "', -1, 0,0, 0, 0, 0, NULL, NULL)";
        DBManager.execCommand(command);
    }

    public Package() {
    }

    public static Package getPackageByID(int ID) throws Exception {
        String query = "Select * From Packages Where PackageID = " + ID;
        return DBManager.getPackageByQuery(query);
    }

    public static void setStatus(int status, int packageID) throws Exception {
        String query = "Update Packages Set Status = " + status + " Where PackageID = " + packageID;
        DBManager.execCommand(query);
    }

    public static void deletePackage(int packageID) throws Exception {
        String command = "Delete From Packages Where PackageID = " + packageID;
        DBManager.execCommand(command);
    }

    public static void setTransferProof(int packageID, String transferProofPhoto) throws Exception {
        String query = "Update Packages Set TransferProofPhoto = ? Where PackageID = " + packageID;
        DBManager.loadPhoto(query, javax.xml.bind.DatatypeConverter.parseBase64Binary(transferProofPhoto));
    }

    public static void setDeliveryProof(int packageID, String deliveryProofPhoto) throws Exception {
        String query = "Update Packages Set DeliveryProofPhoto = ? Where PackageID = " + packageID;
        DBManager.loadPhoto(query, javax.xml.bind.DatatypeConverter.parseBase64Binary(deliveryProofPhoto));
    }

    public static int getProducerIDByPackageID(int packageID) throws Exception {
        String paramName = "ProducerID";
        String query = "Select " + paramName + " From Packages Where PackageID = " + packageID;
        return DBManager.getIntByQuery(query, paramName);
    }

    public static Package parsePackageFromResultSet(ResultSet resultSet) throws Exception {
        Package _package = new Package();
        _package.setConsumerID(resultSet.getInt("ConsumerID"));
        _package.setProducerID(resultSet.getInt("ProducerID"));
        _package.setGetterID(resultSet.getInt("GetterID"));
        _package.setPackageID(resultSet.getInt("PackageID"));
        _package.setStatus(resultSet.getInt("Status"));

        _package.setSourceLatitude(resultSet.getFloat("SourceLatitude"));
        _package.setDestinationLatitude(resultSet.getFloat("DestinationLatitude"));
        _package.setSourceLongitude(resultSet.getFloat("SourceLongitude"));
        _package.setDestinationLongitude(resultSet.getFloat("DestinationLongitude"));
        _package.setDestinationAddress(resultSet.getString("DestinationAddress"));
        _package.setSourceAddress(resultSet.getString("SourceAddress"));

        byte[] deliveryPhotoProof = resultSet.getBytes("DeliveryProofPhoto");
        if (deliveryPhotoProof != null) {
            _package.setDeliveryProofPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(deliveryPhotoProof));
        }

        byte[] transferPhotoProof = resultSet.getBytes("TransferProofPhoto");
        if (transferPhotoProof != null) {
            _package.setTransferProofPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(transferPhotoProof));

        }

        _package.setEventDate(resultSet.getTimestamp("EventDate"));
        _package.setFinishDate(resultSet.getTimestamp("FinishDate"));

        _package.setProducer(User.getUserByID(_package.getProducerID(), false, false, true));
        _package.setConsumer(User.getUserByID(_package.getConsumerID(), false, false, true));

        if (_package.getStatus() != -1) {
            _package.setGetter(User.getUserByID(_package.getGetterID(), false, false, true));
            _package.getGetter().clear();
        }

        _package.getConsumer().clear();
        _package.getProducer().clear();
        return _package;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAdress) {
        this.destinationAddress = destinationAdress;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getGetter() {
        return getter;
    }

    public void setGetter(User getter) {
        this.getter = getter;
    }

    public int getGetterID() {
        return getterID;
    }

    public void setGetterID(int getterID) {
        this.getterID = getterID;
    }

    public float getSourceLongitude() {
        return sourceLongitude;
    }

    public void setSourceLongitude(float sourceLongitude) {
        this.sourceLongitude = sourceLongitude;
    }

    public float getSourceLatitude() {
        return sourceLatitude;
    }

    public void setSourceLatitude(float sourceLatitude) {
        this.sourceLatitude = sourceLatitude;
    }

    public float getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(float destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public float getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(float destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getTransferProofPhoto() {
        return transferProofPhoto;
    }

    public void setTransferProofPhoto(String transferProofPhoto) {
        this.transferProofPhoto = transferProofPhoto;
    }

    public String getDeliveryProofPhoto() {
        return deliveryProofPhoto;
    }

    public void setDeliveryProofPhoto(String deliveryProofPhoto) {
        this.deliveryProofPhoto = deliveryProofPhoto;
    }

}
