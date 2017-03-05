package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Packages.Offer.OfferRequest;
import ru.hse.coursework.models.Packages.Offer.Offers;
import ru.hse.coursework.models.Packages.Offer.PackageOffer;
import ru.hse.coursework.models.Packages.Order.OrderRequest;
import ru.hse.coursework.models.Packages.Order.Orders;
import ru.hse.coursework.models.Packages.Order.PackageOrder;
import ru.hse.coursework.models.Packages.Package;
import ru.hse.coursework.models.Packages.Packages;
import ru.hse.coursework.models.Packages.Requests;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pack")
public class PackageController {

    @POST
    @Path("/mof/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeOffer(@HeaderParam("token") String token,
                                  @HeaderParam("source") String source,
                                  @HeaderParam("destination") String destination,
                                  @HeaderParam("startDate") String startDate,
                                  @HeaderParam("endDate") String endDate,
                                  @HeaderParam("text") String text,
                                  @HeaderParam("length") float length) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            new PackageOffer(user.getPersonID(), source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate), text, length);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/mor/")

    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeOrder(@HeaderParam("token") String token,
                                  @HeaderParam("source") String source,
                                  @HeaderParam("destination") String destination,
                                  @HeaderParam("startDate") String startDate,
                                  @HeaderParam("endDate") String endDate,
                                  @HeaderParam("text") String text,
                                  @HeaderParam("length") int length) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            new PackageOrder(user.getPersonID(), source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate), text, length);
            user.setToken(token);
            user.setLastOnlineDate();

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dor/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOrderByID(@HeaderParam("token") String token,
                                        @HeaderParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            PackageOrder.deletePackageOrder(orderID, user.getPersonID());

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dof/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOfferByID(@HeaderParam("token") String token,
                                        @HeaderParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            PackageOffer.deletePackageOffer(offerID);
            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gofi/")
    @Produces(MediaType.APPLICATION_JSON)
    public PackageOffer getOfferInfo(@HeaderParam("token") String token,
                                     @HeaderParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            PackageOffer offer = PackageOffer.getOfferByID(offerID);
            offer.setDefaultClass(new DefaultClass(true, token));

            user.setToken(token);
            user.setLastOnlineDate();
            return offer;

        } catch (Exception ex) {
            PackageOffer offer = new PackageOffer();
            offer.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offer;
        }
    }

    @POST
    @Path("/gori/")
    @Produces(MediaType.APPLICATION_JSON)
    public PackageOrder getOrderInfo(@HeaderParam("token") String token,
                                     @HeaderParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            PackageOrder order = PackageOrder.getOrderByID(orderID);
            order.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return order;
        } catch (Exception ex) {
            PackageOrder order = new PackageOrder();
            order.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return order;
        }
    }

    @POST
    @Path("/gpi/")
    @Produces(MediaType.APPLICATION_JSON)
    public Package getPackageInfo(@HeaderParam("token") String token,
                                  @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Package _package = Package.getPackageByID(packageID);
            _package.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return _package;
        } catch (Exception ex) {
            Package _package = new Package();
            _package.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return _package;
        }
    }

    @POST
    @Path("/gofby/")
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getOffersByParams(@HeaderParam("token") String token,
                                    @HeaderParam("source") String source,
                                    @HeaderParam("destination") String destination,
                                    @HeaderParam("startDate") String startDate,
                                    @HeaderParam("endDate") String endDate) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Offers offers = Offers.getOffersByParams(source, destination, startDate, endDate);
            offers.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return offers;
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    @POST
    @Path("/gorby/")
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getOrdersByParams(@HeaderParam("token") String token,
                                    @HeaderParam("source") String source,
                                    @HeaderParam("destination") String destination,
                                    @HeaderParam("startDate") String startDate,
                                    @HeaderParam("endDate") String endDate) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Orders orders = Orders.getOrdersByParams(source, destination, startDate, endDate);
            orders.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return orders;
        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    @POST
    @Path("/guor/")
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getUserOrders(@HeaderParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Orders orders = Orders.getOrdersByID(user.getPersonID());
            orders.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return orders;
        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    @POST
    @Path("/guof/")
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getUserOffers(@HeaderParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Offers offers = Offers.getOffersByID(user.getPersonID());
            offers.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return offers;
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    @POST
    @Path("/gup/")
    @Produces(MediaType.APPLICATION_JSON)
    public Packages getUserPackages(@HeaderParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            Packages packages = Packages.getPackagesByUserID(user.getPersonID());
            packages.setDefaultClass(new DefaultClass(true, token));
            user.setToken(token);
            user.setLastOnlineDate();
            return packages;
        } catch (Exception ex) {
            Packages packages = new Packages();
            packages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return packages;
        }
    }

    //соглашение на сделку(отправка реквеста)
    @POST
    @Path("/aor/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOrder(@HeaderParam("token") String token,
                                    @HeaderParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            new OrderRequest(user.getPersonID(), orderID);

            user.setToken(token);
            user.setLastOnlineDate();

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/aof/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOffer(@HeaderParam("token") String token,
                                    @HeaderParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());
            new OfferRequest(user.getPersonID(), offerID);

            user.setToken(token);
            user.setLastOnlineDate();

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    //заключение сделки(вы создатель вам предложили)
    @POST
    @Path("/cofr/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closeOfferRequest(@HeaderParam("token") String token,
                                          @HeaderParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            OfferRequest request = OfferRequest.getRequestByID(requestID);
            PackageOffer offer = PackageOffer.getOfferByID(request.getOfferID());

            new Package(request.getPersonID(), offer.getPersonID(), offer.getSource(), offer.getDestination(), offer.getStartDate(), offer.getEndDate(), offer.getText(), offer.getLength());

            PackageOffer.deletePackageOffer(offer.getOfferID());
            OfferRequest.deleteRequest(requestID);

            user.setToken(token);
            user.setLastOnlineDate();

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/corr/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closeOrderRequest(@HeaderParam("token") String token,
                                          @HeaderParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token);

            token = Service.makeToken(user.getLogin());

            OrderRequest request = OrderRequest.getRequestByID(requestID);
            PackageOrder order = PackageOrder.getOrderByID(request.getOrderID());

            new Package(order.getPersonID(), request.getPersonID(), order.getSource(), order.getDestination(), order.getStartDate(), order.getEndDate(), order.getText(), order.getLength());

            PackageOrder.deletePackageOrder(order.getOrderID(), user.getPersonID());
            OrderRequest.deleteRequest(requestID);

            user.setToken(token);
            user.setLastOnlineDate();

            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gur/")
    @Produces(MediaType.APPLICATION_JSON)
    public Requests getUserRequests(@HeaderParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Requests requests = Requests.getRequestsByPersonID(user.getPersonID());

            user.setToken(token);
            user.setLastOnlineDate();
            requests.setDefaultClass(new DefaultClass(true, token));
            return requests;
        } catch (Exception ex) {
            Requests requests = new Requests();
            requests.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return requests;
        }
    }

    @POST
    @Path("/me/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeEvent(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID, @HeaderParam("Date") String date) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package.setEvent(Service.dateFromString(date), packageID);

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/ae/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptEvent(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package.setStatus(2, packageID);

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/de/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass declineEvent(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package _package = Package.getPackageByID(packageID);

            Package.deletePackage(packageID, user.getPersonID());

            new PackageOrder(_package.getConsumerID(), _package.getSource(), _package.getDestination(), _package.getStartDate(), _package.getEndDate(), _package.getText(), _package.getLength());

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/tp/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass transferPackage(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package.setStatus(3, packageID);

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/pf/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass proofFinish(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package.setStatus(4, packageID);

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/cp/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closePackage(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package.setStatus(5, packageID);

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dp/")
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deletePackage(@HeaderParam("token") String token, @HeaderParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token);
            token = Service.makeToken(user.getLogin());

            Package _package = Package.getPackageByID(packageID);

            //Deleting

            new PackageOrder(_package.getConsumerID(), _package.getSource(), _package.getDestination(), _package.getStartDate(), _package.getEndDate(), _package.getText(), _package.getLength());

            user.setToken(token);
            user.setLastOnlineDate();
            return new DefaultClass(true, token);
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

//    @GET
//    @Path("/mof/t={token}&s={source}&d={destination}&sd={startdate}&ed={enddate}&t={text}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass makeOffer(@PathParam("token") String token,
//                                  @PathParam("source") String source,
//                                  @PathParam("destination") String destination,
//                                  @PathParam("startdate") String startDate,
//                                  @PathParam("enddate") String endDate,
//                                  @PathParam("text") String text) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            new PackageOffer(user.getPersonID(), source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate), text);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/mor/t={token}&s={source}&d={destination}&sd={startdate}&ed={enddate}&t={text}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass makeOrder(@PathParam("token") String token,
//                                  @PathParam("source") String source,
//                                  @PathParam("destination") String destination,
//                                  @PathParam("startdate") String startDate,
//                                  @PathParam("enddate") String endDate,
//                                  @PathParam("text") String text) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            new PackageOrder(user.getPersonID(), source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate), text);
//            user.setToken(token);
//            user.setLastOnlineDate();
//
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/dor/t={token}&id={ID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass deleteOrderByID(@PathParam("token") String token,
//                                        @PathParam("ID") int OrderID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            PackageOrder.deletePackageOrder(OrderID);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/dof/t={token}&id={ID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass deleteOfferByID(@PathParam("token") String token,
//                                        @PathParam("ID") int OfferID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            PackageOffer.deletePackageOffer(OfferID);
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/gofi/t={token}&o={offerID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public PackageOffer getOfferInfo(@PathParam("token") String token,
//                                     @PathParam("offerID") int offerID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            PackageOffer offer = PackageOffer.getOfferByID(offerID);
//            offer.setDefaultClass(new DefaultClass(true, token));
//
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return offer;
//
//        } catch (Exception ex) {
//            PackageOffer offer = new PackageOffer();
//            offer.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return offer;
//        }
//    }
//
//    @GET
//    @Path("/gori/t={token}&o={orderID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public PackageOrder getOrderInfo(@PathParam("token") String token,
//                                     @PathParam("orderID") int orderID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            PackageOrder order = PackageOrder.getOrderByID(orderID);
//            order.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return order;
//        } catch (Exception ex) {
//            PackageOrder order = new PackageOrder();
//            order.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return order;
//        }
//    }
//
//    @GET
//    @Path("/gpi/t={token}&o={packageID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Package getPackageInfo(@PathParam("token") String token,
//                                  @PathParam("packageID") int packageID){
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Package _package = Package.getPackageByID(packageID);
//            _package.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return _package;
//        } catch (Exception ex) {
//            Package _package = new Package();
//            _package.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return _package;
//        }
//    }
//
//    @GET
//    @Path("/gofby/t={token}&s={source}&d={destination}&sda={startdate}&eda={enddate}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Offers getOffersByParams(@PathParam("token") String token,
//                                    @PathParam("source") String source,
//                                    @PathParam("destination") String destination,
//                                    @PathParam("startdate") String startdate,
//                                    @PathParam("enddate") String enddate) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Offers offers = Offers.getOffersByParams(source, destination, startdate, enddate);
//            offers.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return offers;
//        } catch (Exception ex) {
//            Offers offers = new Offers();
//            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return offers;
//        }
//    }
//
//    @GET
//    @Path("/gorby/t={token}&s={source}&d={destination}&sda={startdate}&eda={enddate}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Orders getOrdersByParams(@PathParam("token") String token,
//                                    @PathParam("source") String source,
//                                    @PathParam("destination") String destination,
//                                    @PathParam("startdate") String startdate,
//                                    @PathParam("enddate") String enddate) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Orders orders = Orders.getOrdersByParams(source, destination, startdate, enddate);
//            orders.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return orders;
//        } catch (Exception ex) {
//            Orders orders = new Orders();
//            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return orders;
//        }
//    }
//
//    @GET
//    @Path("/guor/t={token}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Orders getUserOrders(@PathParam("token") String token) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Orders orders = Orders.getOrdersByID(user.getPersonID());
//            orders.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return orders;
//        } catch (Exception ex) {
//            Orders orders = new Orders();
//            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return orders;
//        }
//    }
//
//    @GET
//    @Path("/guof/t={token}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Offers getUserOffers(@PathParam("token") String token) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Offers offers = Offers.getOffersByID(user.getPersonID());
//            offers.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return offers;
//        } catch (Exception ex) {
//            Offers offers = new Offers();
//            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return offers;
//        }
//    }
//
//    @GET
//    @Path("/gup/t={token}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Packages getUserPackages(@PathParam("token") String token){
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            Packages packages = Packages.getPackagesByUserID(user.getPersonID());
//            packages.setDefaultClass(new DefaultClass(true, token));
//            user.setToken(token);
//            user.setLastOnlineDate();
//            return packages;
//        } catch (Exception ex) {
//            Packages packages = new Packages();
//            packages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
//            return packages;
//        }
//    }
//
//    //соглашение на сделку(отправка реквеста)
//    @GET
//    @Path("/aor/t={token}&o={orderID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass acceptOrder(@PathParam("token") String token,
//                                    @PathParam("orderID") int orderID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            new OrderRequest(user.getPersonID(), orderID);
//
//            user.setToken(token);
//            user.setLastOnlineDate();
//
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//        //отправка реквеста
//    }
//
//    @GET
//    @Path("/aof/t={token}&o={offerID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass acceptOffer(@PathParam("token") String token,
//                                    @PathParam("offerID") int offerID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//            new OfferRequest(user.getPersonID(), offerID);
//
//            user.setToken(token);
//            user.setLastOnlineDate();
//
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    //заключение сделки(вы создатель вам предложили)
//    @GET
//    @Path("/cofr/t={token}&rid={requestID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass closeOfferRequest(@PathParam("token") String token,
//                                          @PathParam("requestID") int requestID) {
//        try {
//            User user = User.getUserByToken(token);
//            token = Service.makeToken(user.getLogin());
//
//            OfferRequest request = OfferRequest.getRequestByID(requestID);
//            PackageOffer offer = PackageOffer.getOfferByID(request.getOfferID());
//
//            new Package(request.getPersonID(), offer.getPersonID(), offer.getSource(), offer.getDestination(), offer.getStartDate(), offer.getEndDate(), offer.getText());
//
//            PackageOffer.deletePackageOffer(offer.getOfferID());
//            OfferRequest.deleteRequest(requestID);
//
//            user.setToken(token);
//            user.setLastOnlineDate();
//
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
//
//    @GET
//    @Path("/corr/t={token}&rid={requestID}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public DefaultClass closeOrderRequest(@PathParam("token") String token,
//                                          @PathParam("requestID") int requestID) {
//        try {
//            User user = User.getUserByToken(token);
//
//            token = Service.makeToken(user.getLogin());
//
//            OrderRequest request = OrderRequest.getRequestByID(requestID);
//            PackageOrder order = PackageOrder.getOrderByID(request.getOrderID());
//
//            new Package(order.getPersonID(), request.getPersonID(), order.getSource(), order.getDestination(), order.getStartDate(), order.getEndDate(), order.getText());
//
//            PackageOrder.deletePackageOrder(order.getOrderID());
//            OrderRequest.deleteRequest(requestID);
//
//            user.setToken(token);
//            user.setLastOnlineDate();
//
//            return new DefaultClass(true, token);
//        } catch (Exception ex) {
//            return new DefaultClass(false, ex.getMessage());
//        }
//    }
}
