package ru.hse.coursework.models.Event;


import ru.hse.coursework.models.Service.DefaultClass;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
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
