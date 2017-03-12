package ru.hse.coursework.models.Packages.Order;

import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@XmlRootElement
public class Orders implements Serializable {

    private ArrayList<PackageOrder> orders;
    private DefaultClass defaultClass;

    public static Orders getOrdersByParams(String source, String destination, Date startDate, Date endDate) throws Exception {
        String query = "Select * From Orders Where (Source = '" + source + "') AND (Destination = '" + destination + "') AND (StartDate >= '" + Service.makeSqlDateString(startDate) + "') AND (EndDate <= '" + Service.makeSqlDateString(endDate) + " 23:59')";
        return Service.getOrdersByQuery(query);
    }

    public static Orders getOrdersByID(int ID) throws Exception {
        String query = "Select * From Orders Where OrderID = " + ID;
        return Service.getOrdersByQuery(query);
    }

    public void setDefaultClass(DefaultClass defaultClass) {
        this.defaultClass = defaultClass;
    }

    public DefaultClass getDefaultClass() {
        return defaultClass;
    }

    public ArrayList<PackageOrder> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<PackageOrder> orders) {
        this.orders = orders;
    }
}
