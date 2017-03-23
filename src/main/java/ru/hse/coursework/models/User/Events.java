package ru.hse.coursework.models.User;


import ru.hse.coursework.models.Service.DefaultClass;

import java.util.ArrayList;

public class Events {
    private ArrayList<Event> events;
    private DefaultClass defaultClass;

    public Events(ArrayList<Event> events, DefaultClass defaultClass) {
        this.events = events;
        this.defaultClass = defaultClass;
    }

    public Events() {
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

}
