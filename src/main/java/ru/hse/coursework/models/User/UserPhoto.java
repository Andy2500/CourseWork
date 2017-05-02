package ru.hse.coursework.models.User;

import ru.hse.coursework.models.Service.DefaultClass;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserPhoto {
    private String photo;
    private DefaultClass defaultClass;

    public UserPhoto() {

    }

    public UserPhoto(String photo, DefaultClass defaultClass) {
        this.photo = photo;
        this.defaultClass = defaultClass;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }


}
