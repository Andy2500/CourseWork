package ru.hse.coursework.models.User;


import ru.hse.coursework.models.Service.Service;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private int eventID;
    private int personID;
    private Date date;
    private String text;

    public static ArrayList<Event> getEventsByPersonIDFromDate(int personID, Date date) throws Exception {
        String query = "Select * From Packages Where PersonID = " + personID + "AND Date > '" + Service.makeSqlDateString(date) + "'";
        return Service.getEventsByQuery(query);
    }

    public static void writeEvent(String text, int personID) throws Exception {
        String command = "Insert Into Events (EventID, PersonID, Date, String) Values ((Select Max(EventID) From Events) + 1, " + personID + ",'" + Service.makeSqlDateString(new Date()) + "','" + text + "')";
        Service.execCommand(command);
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
