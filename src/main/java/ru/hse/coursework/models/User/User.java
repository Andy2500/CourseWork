package ru.hse.coursework.models.User;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class User implements Serializable {

    private int personID;

    private String login;
    private String email;
    private String name;
    private String hashpsd;
    private String phone;
    private String token;
    private String photo;

    private String documentPhoto;

    private int countOfOrders;
    private int countOfOffers;
    private int countOfPackages;
    private int countOfResponses;
    private int sumOfResponses;

    private int status; //1 - заблокирован, 0 - не подтвержден, 1 - подтвержден, 2 - исполнитель, 3 - модератор

    private float lastLatitude;
    private float lastLongitude;

    private Date lastOnlineDate;

    private DefaultClass defaultClass;

    public User() {
    }

    public User(int personID, String login, String email, String name, String hashpsd, String phone, String token, String photo, String documentPhoto, int countOfOrders, int countOfOffers, int countOfPackages, int countOfResponses, int sumOfResponses, int status, float lastLatitude, float lastLongitude, Date lastOnlineDate) {
        this.personID = personID;
        this.login = login;
        this.email = email;
        this.name = name;
        this.hashpsd = hashpsd;
        this.phone = phone;
        this.token = token;
        this.photo = photo;
        this.documentPhoto = documentPhoto;
        this.countOfOrders = countOfOrders;
        this.countOfOffers = countOfOffers;
        this.countOfPackages = countOfPackages;
        this.countOfResponses = countOfResponses;
        this.sumOfResponses = sumOfResponses;
        this.status = status;
        this.lastLatitude = lastLatitude;
        this.lastLongitude = lastLongitude;
        this.lastOnlineDate = lastOnlineDate;
    }

    public static void exists(String login, String phone, String email) throws Exception {
        String query = "Select * From Users Where Login = '" + login + "'";
        User user = Service.getUserByQuery(query);

        if (user.getPersonID() != 0) {
            throw new Exception("Login Error");
        }

        query = "Select * From Users Where Phone = '" + phone + "'";
        user = Service.getUserByQuery(query);

        if (user.getPersonID() != 0) {
            throw new Exception("Phone Error");
        }

        query = "Select * From Users Where Email = '" + email + "'";
        user = Service.getUserByQuery(query);

        if (user.getPersonID() != 0) {
            throw new Exception("Email Error");
        }
    }

    public User(String login, String email, String name, String hashpsd, String phone, String token) throws Exception {
        this.login = login;
        this.email = email;
        this.name = name;
        this.hashpsd = hashpsd;
        this.phone = phone;
        String command = "Insert Into Users (PersonID,Login,Email,Name,HashPassword,Phone,Photo, LastOnlineDate, Token, Status, DocumentPhoto, LastLatitude, LastLongitude)"
                + "Values ((Select MAX(PersonID) FROM Users) + 1,'" + login + "','" + email + "','" + name + "','" + hashpsd + "','" + phone + "',NULL,'" + Service.getNowMomentInUTC() + "','" + token + "',0,NULL,0,0)";
        Service.execCommand(command);
    }

    public void clear() {
        this.email = null;
        this.hashpsd = null;
        this.phone = null;
        this.token = null;
        this.defaultClass = null;
        this.documentPhoto = null;
    }

    public void cleanForUser() {
        this.hashpsd = null;
        this.token = null;
        this.defaultClass = null;
    }

    //получение пользователя по ID
    public static User getUserByID(int ID) throws Exception {
        String query = "Select * From Users Where PersonID = " + ID;
        User user = Service.getUserByQuery(query);

        if (user.getPersonID() == 0) {
            throw new Exception("ID Error");
        }

        return user;
    }

    //получение пользователя по токену
    public static User getUserByToken(String token) throws Exception {
        String query = "Select * From Users Where Token = '" + token + "'";
        User user = Service.getUserByQuery(query);

        if (user.getPersonID() == 0) {
            throw new Exception("Token Error");
        }

        return user;
    }

    //получение пользователя по ID
    public static User getUserByLogin(String login) throws Exception {
        String query = "Select * From Users Where Login = '" + login + "'";
        User user = Service.getUserByQuery(query);

        if (user.getPersonID() == 0) {
            throw new Exception("Login Error");
        }
        return user;
    }

    public static void setLogin(int personID, String login) throws Exception {
        String command = "Update Users Set Login = '" + login + "' Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public static void setEmail(User user, String email, String last) throws Exception {
        //запись в базу данных
        if (!user.email.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            user.email = email;
            String command = " Update Users Set Email = '" + email + "' Where PersonID = " + user.personID;
            Service.execCommand(command);
        }
    }

    public static void setName(int personID, String name) throws Exception {
        String command = "Update Users Set Name = '" + name + "' Where PersonID=" + personID;
        Service.execCommand(command);
    }

    public static void setDocumentPhoto(int personID, String documentPhoto) throws Exception {
        String query = "Update Users Set DocumentPhoto = ? Where PersonID = " + personID;
        Service.loadPhoto(query, javax.xml.bind.DatatypeConverter.parseBase64Binary(documentPhoto));
    }

    public static void setHashpsd(User user, String hashpsd, String last) throws Exception {

        if (!user.hashpsd.equals(last)) {
            throw new Exception("Last Psd Error");
        } else {
            user.hashpsd = hashpsd;
            String command = "Update Users Set HashPassword = '" + hashpsd + "' Where PersonID=" + user.personID;
            Service.execCommand(command);
        }
    }

    public static void setPhone(User user, String phone, String last) throws Exception {
        if (!user.phone.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            user.phone = phone;
            String command = "Update Users Set Phone = '" + phone + "' Where PersonID=" + user.personID;
            Service.execCommand(command);
        }
    }

    public static void setToken(int personID, String token) throws Exception {
        String command = "Update Users Set Token = '" + token + "' Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public static void setLastOnlineDate(int personID) throws Exception {
        String command = "Update Users Set LastOnlineDate = '" + Service.getNowMomentInUTC() + "' Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public static void setPhoto(int personID, String photo) throws Exception {
        String query = "Update Users Set Photo = ? Where PersonID = " + personID;
        Service.loadPhoto(query, javax.xml.bind.DatatypeConverter.parseBase64Binary(photo));
    }

    public static void setStatus(int personID, int status) throws Exception {
        String command = "Update Users Set Status = " + status + " Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public static void setCoordinates(int personID, float lastLongitude, float lastLatitude) throws Exception {
        String command = "Update Users Set LastLatitude = " + lastLatitude + ", LastLongitude = " + lastLongitude + " Where PersonID = " + personID;
        Service.execCommand(command);
    }

    public static int getCountOfOpenProducerPackagesByPersonID(int personID) throws Exception {
        String query = "SELECT COUNT(*) " + " FROM Packages Where ProducerID = " + personID + " AND Status = 1";
        return Service.getIntByQuery(query, "");
    }


    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashpsd() {
        return hashpsd;
    }

    public void setHashpsd(String hashpsd) {
        this.hashpsd = hashpsd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDocumentPhoto() {
        return documentPhoto;
    }

    public void setDocumentPhoto(String documentPhoto) {
        this.documentPhoto = documentPhoto;
    }

    public int getCountOfOrders() {
        return countOfOrders;
    }

    public void setCountOfOrders(int countOfOrders) {
        this.countOfOrders = countOfOrders;
    }

    public int getCountOfOffers() {
        return countOfOffers;
    }

    public void setCountOfOffers(int countOfOffers) {
        this.countOfOffers = countOfOffers;
    }

    public int getCountOfPackages() {
        return countOfPackages;
    }

    public void setCountOfPackages(int countOfPackages) {
        this.countOfPackages = countOfPackages;
    }

    public int getCountOfResponses() {
        return countOfResponses;
    }

    public void setCountOfResponses(int countOfResponses) {
        this.countOfResponses = countOfResponses;
    }

    public int getSumOfResponses() {
        return sumOfResponses;
    }

    public void setSumOfResponses(int sumOfResponses) {
        this.sumOfResponses = sumOfResponses;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(float lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public float getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(float lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public Date getLastOnlineDate() {
        return lastOnlineDate;
    }

    public void setLastOnlineDate(Date lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
