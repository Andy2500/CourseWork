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

    public boolean isExists() {
        return false;
    }

    public User(String login, String email, String name, String hashpsd, String phone) throws Exception {
        this.login = login;
        this.email = email;
        this.name = name;
        this.hashpsd = hashpsd;
        this.phone = phone;
        String command = "Insert Into Users (PersonID,Login,Email,Name,HashPassword,Phone,Photo, CountOfOrders, CountOFOffers, Experience, Rank, LastOnlineDate)"
                + "Values ((Select Max(PersonID) From Users) + 1,'" + login + "','" + email + "','" + name + "','" + hashpsd + "','" + phone + "',NULL,0,0,0,0,'" + Service.getNowMomentInUTC() + "')";
        Service.execCommand(command);
    }

    public void clear() {
        this.email = null;
        this.hashpsd = null;
        this.phone = null;
        this.token = null;
        this.defaultClass = null;
    }

    //получение пользователя по ID
    public static User getUserByID(int ID) throws Exception {
        String query = "Select * From Users Where PersonID = " + ID;
        return Service.getUserByQuery(query);
    }

    //получение пользователя по токену
    public static User getUserByToken(String token) throws Exception {
        String query = "Select * From Users Where Token = " + token;
        return Service.getUserByQuery(query);
    }

    //получение пользователя по ID
    public static User getUserByLogin(String login) throws Exception {
        String query = "Select * From Users Where Login = " + login;
        return Service.getUserByQuery(query);
    }

    public void setLogin(String login) throws Exception {
        this.login = login;
        String command = "Update Users Set Login = " + login + " Where PeronID = " + this.personID;
        Service.execCommand(command);
    }

    public void setEmail(String email, String last) throws Exception {
        //запись в базу данных
        if (this.email.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            this.email = email;
            String command = " Update Users Set Email = " + email + " Where PeronID = " + this.personID;
            Service.execCommand(command);
        }
    }

    public void setName(String name) throws Exception {
        this.name = name;
        String command = "Update Users Set Name =" + name + " Where PeronID=" + this.personID;
        Service.execCommand(command);
    }

    public void setHashpsd(String hashpsd, String last) throws Exception {

        if (this.hashpsd.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            this.hashpsd = hashpsd;
            String command = "Update Users Set HashPassword = " + hashpsd + " Where PeronID=" + this.personID;
            Service.execCommand(command);
        }
    }

    public void setPhone(String phone, String last) throws Exception {
        if (this.phone.equals(last)) {
            throw new Exception("Last Email Error");
        } else {
            this.phone = phone;
            String command = "Update Users Set HashPassword = @Password Where PeronID=" + personID;
            Service.execCommand(command);
        }
    }

    public void setToken(String token) throws Exception {
        this.token = token;
        String command = "Update Users Set Token = " + token + " Where PeronID = " + this.personID;
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
}
