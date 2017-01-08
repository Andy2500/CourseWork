package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Packages.Offer.OfferRequest;
import ru.hse.coursework.models.Packages.Offer.Offers;
import ru.hse.coursework.models.Packages.Offer.PackageOffer;
import ru.hse.coursework.models.Packages.Order.OrderRequest;
import ru.hse.coursework.models.Packages.Order.Orders;
import ru.hse.coursework.models.Packages.Order.PackageOrder;
import ru.hse.coursework.models.Packages.Package;
import ru.hse.coursework.models.Packages.Packages;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/pack")
public class PackageController {
    @GET
    @Path("/mof/t={token}&s={source}&d={destination}&da={date}&t={text}")
    public DefaultClass makeOffer(@PathParam("token") String token,
                                  @PathParam("source") String source,
                                  @PathParam("destination") String destination,
                                  @PathParam("date") String date,
                                  @PathParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            new PackageOffer(user.getPersonID(), source, destination, date, text);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/mor/t={token}&s={source}&d={destination}&da={date}&t={text}")
    public DefaultClass makeOrder(@PathParam("token") String token,
                                  @PathParam("source") String source,
                                  @PathParam("destination") String destination,
                                  @PathParam("date") String date,
                                  @PathParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            new PackageOrder(user.getPersonID(), source, destination, date, text);
            user.setToken(token);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/dor/t={token}&id={ID}")
    public DefaultClass deleteOrderByID(@PathParam("token") String token,
                                        @PathParam("ID") int OrderID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            PackageOrder.deletePackageOrder(OrderID);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/dof/t={token}&id={ID}")
    public DefaultClass deleteOfferByID(@PathParam("token") String token,
                                        @PathParam("ID") int OfferID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            PackageOffer.deletePackageOffer(OfferID);
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/gofi/t={token}&o={offerID}")
    public PackageOffer getOfferInfo(@PathParam("token") String token,
                                     @PathParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);
            PackageOffer offer = PackageOffer.getOfferByID(offerID);
            offer.setDefaultClass(new DefaultClass(true, token));
            return offer;

        } catch (Exception ex) {
            PackageOffer offer = new PackageOffer();
            offer.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offer;
        }
    }

    @GET
    @Path("/gorfi/t={token}&o={orderID}")
    public PackageOrder getOrderInfo(@PathParam("token") String token,
                                     @PathParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            PackageOrder order = PackageOrder.getOrderByID(orderID);
            order.setDefaultClass(new DefaultClass(true, token));
            return order;
        } catch (Exception ex) {
            PackageOrder order = new PackageOrder();
            order.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return order;
        }
    }

    @GET
    @Path("/gpi/t={token}&o={packageID}")
    public Package getPackageInfo(@PathParam("token") String token,
                                  @PathParam("packageID") int packageID){
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Package _package = Package.getPackageByID(packageID);
            _package.setDefaultClass(new DefaultClass(true, token));
            return _package;
        } catch (Exception ex) {
            Package _package = new Package();
            _package.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return _package;
        }
    }

    @GET
    @Path("/gofby/t={token}&s={source}&d={destination}&sda={startdate}&eda={enddate}")
    public Offers getOffersByParams(@PathParam("token") String token,
                                    @PathParam("source") String source,
                                    @PathParam("destination") String destination,
                                    @PathParam("startdate") String startdate,
                                    @PathParam("enddate") String enddate) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Offers offers = Offers.getOffersByParams(source, destination, startdate, enddate);
            offers.setDefaultClass(new DefaultClass(true, token));
            return offers;
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    @GET
    @Path("/gorby/t={token}&s={source}&d={destination}&sda={startdate}&eda={enddate}")
    public Orders getOrdersByParams(@PathParam("token") String token,
                                    @PathParam("source") String source,
                                    @PathParam("destination") String destination,
                                    @PathParam("startdate") String startdate,
                                    @PathParam("enddate") String enddate) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Orders orders = Orders.getOrdersByParams(source, destination, startdate, enddate);
            orders.setDefaultClass(new DefaultClass(true, token));
            return orders;
        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    @GET
    @Path("/guor/t={token}")
    public Orders getUserOrders(@PathParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Orders orders = Orders.getOrdersByID(user.getPersonID());
            orders.setDefaultClass(new DefaultClass(true, token));
            return orders;
        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    @GET
    @Path("/guof/t={token}")
    public Offers getUserOffers(@PathParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Offers offers = Offers.getOffersByID(user.getPersonID());
            offers.setDefaultClass(new DefaultClass(true, token));
            return offers;
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    @GET
    @Path("/gup/t={token}")
    public Packages getUserPackages(@PathParam("token") String token){
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            Packages packages = Packages.getPackagesByUserID(user.getPersonID());
            packages.setDefaultClass(new DefaultClass(true, token));
            return packages;
        } catch (Exception ex) {
            Packages packages = new Packages();
            packages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return packages;
        }
    }

    //соглашение на сделку(отправка реквеста)
    @GET
    @Path("/aor/t={token}&o={orderID}")
    public DefaultClass acceptOrder(@PathParam("token") String token,
                                    @PathParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            new OrderRequest(user.getPersonID(), orderID);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
        //отправка реквеста
    }

    @GET
    @Path("/aof/t={token}&o={offerID}")
    public DefaultClass acceptOffer(@PathParam("token") String token,
                                    @PathParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            new OfferRequest(user.getPersonID(), offerID);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    //заключение сделки(вы создатель вам предложили)
    @GET
    @Path("/cofr/t={token}&rid={requestID}")
    public DefaultClass closeOfferRequest(@PathParam("token") String token,
                                          @PathParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            OfferRequest request = OfferRequest.getRequestByID(requestID);
            PackageOffer offer = PackageOffer.getOfferByID(request.getOfferID());

            new Package(request.getPersonID(), offer.getPersonID(), offer.getSource(), offer.getDestination(), offer.getDate(), offer.getText());

            PackageOffer.deletePackageOffer(offer.getOfferID());
            OfferRequest.deleteRequest(requestID);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @GET
    @Path("/corr/t={token}&rid={requestID}")
    public DefaultClass closeOrderRequest(@PathParam("token") String token,
                                          @PathParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            user.setToken(token);

            OrderRequest request = OrderRequest.getRequestByID(requestID);
            PackageOrder order = PackageOrder.getOrderByID(request.getOrderID());

            new Package(order.getPersonID(), request.getPersonID(), order.getSource(), order.getDestination(), order.getDate(), order.getText());

            PackageOrder.deletePackageOrder(order.getOrderID());
            OrderRequest.deleteRequest(requestID);

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }
}
