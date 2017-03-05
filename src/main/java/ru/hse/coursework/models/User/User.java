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

    private int countOfOrders;
    private int countOfOffers;
    private int experience;
    private int rank;

    private Date lastOnlineDate;
    private DefaultClass defaultClass;

    public User() {
    }

    public User(int personID, int countOfOrders, int countOfOffers, int experience, int rank, Date lastOnlineDate, String login, String email, String name, String hashpsd, String phone, String token) {
        this.personID = personID;
        this.countOfOrders = countOfOrders;
        this.countOfOffers = countOfOffers;
        this.experience = experience;
        this.rank = rank;
        this.lastOnlineDate = lastOnlineDate;
        this.login = login;
        this.email = email;
        this.name = name;
        this.hashpsd = hashpsd;
        this.phone = phone;
        this.token = token;
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

    public User(String login, String email, String name, String hashpsd, String phone) throws Exception {
        this.login = login;
        this.email = email;
        this.name = name;
        this.hashpsd = hashpsd;
        this.phone = phone;
        String command = "Insert Into Users (PersonID,Login,Email,Name,HashPassword,Phone,Photo, CountOfOrders, CountOFOffers, Experience, Rank, LastOnlineDate, Token)"
                + "Values ((Select MAX(PersonID) FROM Users) + 1,'" + login + "','" + email + "','" + name + "','" + hashpsd + "','" + phone + "',NULL,0,0,0,0,'" + Service.getNowMomentInUTC() + "', '')";
        Service.execCommand(command);
    }

    public void clear() {
        this.email = null;
        this.hashpsd = null;
        this.phone = null;
        this.token = null;
        this.defaultClass = null;
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

    public void setLogin(String login) throws Exception {
        this.login = login;
        String command = "Update Users Set Login = '" + login + "' Where PersonID = " + this.personID;
        Service.execCommand(command);
    }

    public void setEmail(String email, String last) throws Exception {
        //запись в базу данных
        if (!this.email.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            this.email = email;
            String command = " Update Users Set Email = '" + email + "' Where PersonID = " + this.personID;
            Service.execCommand(command);
        }
    }

    public void setName(String name) throws Exception {
        this.name = name;
        String command = "Update Users Set Name = '" + name + "' Where PersonID=" + this.personID;
        Service.execCommand(command);
    }

    public void setHashpsd(String hashpsd, String last) throws Exception {

        if (!this.hashpsd.equals(last)) {
            throw new Exception("Last Psd Error");
        } else {
            this.hashpsd = hashpsd;
            String command = "Update Users Set HashPassword = '" + hashpsd + "' Where PersonID=" + this.personID;
            Service.execCommand(command);
        }
    }

    public void setPhone(String phone, String last) throws Exception {
        if (!this.phone.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            this.phone = phone;
            String command = "Update Users Set Phone = '" + phone + "' Where PersonID=" + personID;
            Service.execCommand(command);
        }
    }

    public void setToken(String token) throws Exception {
        this.token = token;
        String command = "Update Users Set Token = '" + token + "' Where PersonID = " + this.personID;
        Service.execCommand(command);
    }

    public void setLastOnlineDate() throws Exception {
        this.lastOnlineDate = new Date();
        String command = "Update Users Set LastOnlineDate = '" + Service.getNowMomentInUTC() + "' Where PersonID = " + this.personID;
        Service.execCommand(command);
    }

    public int getPersonID() {
        return personID;
    }

    public String getLogin() {
        return login;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getHashpsd() {
        return hashpsd;
    }

    public String getPhone() {
        return phone;
    }

    public int getCountOfOrders() {
        return countOfOrders;
    }

    public int getCountOfOffers() {
        return countOfOffers;
    }

    public int getExperience() {
        return experience;
    }

    public int getRank() {
        return rank;
    }

    public Date getLastOnlineDate() {
        return lastOnlineDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public void setHashpsd(String hashpsd) {
        this.hashpsd = hashpsd;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCountOfOrders(Integer countOfOrders) {
        this.countOfOrders = countOfOrders;
    }

    public void setCountOfOffers(Integer countOfOffers) {
        this.countOfOffers = countOfOffers;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setLastOnlineDate(Date lastOnlineDate) {
        this.lastOnlineDate = lastOnlineDate;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public void setCountOfOrders(int countOfOrders) {
        this.countOfOrders = countOfOrders;
    }

    public void setCountOfOffers(int countOfOffers) {
        this.countOfOffers = countOfOffers;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) throws Exception {
        this.photo = photo;
        String query = "Update Users Set Photo = ? Where PersonID = " + personID;

        Service.loadPhoto(query, javax.xml.bind.DatatypeConverter.parseBase64Binary(photo));
    }
}
