package ru.hse.coursework.models.Packages;

import ru.hse.coursework.models.Packages.Offer.OfferRequest;
import ru.hse.coursework.models.Packages.Order.OrderRequest;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import java.util.ArrayList;

public class Requests {
    private ArrayList<OfferRequest> offerRequests;
    private ArrayList<OrderRequest> orderRequests;
    private DefaultClass defaultClass;

    public static Requests getRequestsByPersonID(int ID) throws Exception {
        Requests requests = new Requests();
        String query = "Select * From OfferRequests Where PersonID = " + ID;
        requests.offerRequests = Service.getOfferRequestsByQuery(query);
        query = "Select * From OrderRequests Where PersonID = " + ID;
        requests.orderRequests = Service.getOrderRequestsByQuery(query);
        return requests;
    }

    public ArrayList<OfferRequest> getOfferRequests() {
        return offerRequests;
    }

    public void setOfferRequests(ArrayList<OfferRequest> offerRequests) {
        this.offerRequests = offerRequests;
    }

    public ArrayList<OrderRequest> getOrderRequests() {
        return orderRequests;
    }

    public void setOrderRequests(ArrayList<OrderRequest> orderRequests) {
        this.orderRequests = orderRequests;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }
}
