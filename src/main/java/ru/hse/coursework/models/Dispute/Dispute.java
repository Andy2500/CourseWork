package ru.hse.coursework.models.Dispute;

import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DBManager;
import ru.hse.coursework.service.DateWorker;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.ResultSet;
import java.util.Date;

@XmlRootElement
public class Dispute {
    private int disputeID;
    private int packageID;
    private int type; //1 - Доставка не было осуществлена 2 - Доставка не подтверждена получателем, 3 - Товар не передан, пруф залит
    private int personID;
    private int status; //0 - открыт, 1 - закрыт
    private String text;

    private Date publishDate;
    private User person;

    public Dispute() {
    }

    public Dispute(int packageID, int type, int personID, String text) throws Exception {
        this.packageID = packageID;
        this.type = type;
        this.personID = personID;
        this.text = text;

        String command = "insert into Disputes ( DisputeID, PackageID, Type, PersonID, Status, Text, PublishDate) " +
                "values ((Select MAX(DisputeID) FROM Disputes) + 1," + packageID + "," + type + "," + personID + ", 0,'" + text + "','" + DateWorker.getNowMomentInUTC() + "')";
        DBManager.execCommand(command);
    }

    public static void changeStatus(int disputeID, int status) throws Exception {
        String command = "update Disputes set Status=\'" + status + "\' where DisputeID= \'" + disputeID + "\'";
        DBManager.execCommand(command);
    }

    public static Dispute parseDisputeFromResultSet(ResultSet resultSet) throws Exception {
        Dispute dispute = new Dispute();
        dispute.setStatus(resultSet.getInt("Status"));
        dispute.setDisputeID(resultSet.getInt("DisputeID"));
        dispute.setPublishDate(resultSet.getTimestamp("PublishDate"));
        dispute.setPackageID(resultSet.getInt("PackageID"));
        dispute.setPersonID(resultSet.getInt("PersonID"));
        dispute.setText(resultSet.getString("Text"));
        dispute.setPerson(User.getUserByID(dispute.getPersonID(), false, false, true));
        return dispute;
    }

    public int getDisputeID() {
        return disputeID;
    }

    public void setDisputeID(int disputeID) {
        this.disputeID = disputeID;
    }

    public int getPackageID() {
        return packageID;
    }

    public void setPackageID(int packageID) {
        this.packageID = packageID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
