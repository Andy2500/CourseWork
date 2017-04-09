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
import ru.hse.coursework.models.User.Event;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/pack")
public class PackageController {


    @POST
    @Path("/mof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeOffer(@FormParam("token") String token,
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("source") String source,
                                  @FormParam("destination") String destination,
                                  @FormParam("startDate") String startDate,
                                  @FormParam("endDate") String endDate,
                                  @FormParam("text") String text,
                                  @FormParam("length") float length) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new PackageOffer(user.getPersonID(), source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate), text, length);
                user.setLastOnlineDate();
                Event.writeEvent("Вы создали заказ: " + source + "->" + destination, user.getPersonID());
                return new DefaultClass(true, token);
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/mor/")

    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeOrder(@FormParam("token") String token,
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("source") String source,
                                  @FormParam("destination") String destination,
                                  @FormParam("startDate") String startDate,
                                  @FormParam("endDate") String endDate,
                                  @FormParam("text") String text,
                                  @FormParam("length") int length) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new PackageOrder(user.getPersonID(), source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate), text, length);
                user.setLastOnlineDate();
                Event.writeEvent("Вы создали предложение: " + source + "->" + destination, user.getPersonID());
                return new DefaultClass(true, "");
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dor/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOrderByID(@FormParam("token") String token,
                                        @FormParam("personID") int id,
                                        @FormParam("date") String date,
                                        @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                PackageOrder.deletePackageOrder(orderID, user.getPersonID());
                user.setLastOnlineDate();
                Event.writeEvent("Вы создали удалили свой заказ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOfferByID(@FormParam("token") String token,
                                        @FormParam("personID") int id,
                                        @FormParam("date") String date,
                                        @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                PackageOffer.deletePackageOffer(offerID);
                user.setLastOnlineDate();
                Event.writeEvent("Вы создали удалили свое предложение", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    //соглашение на сделку(отправка реквеста)
    @POST
    @Path("/aor/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOrder(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new OrderRequest(user.getPersonID(), orderID);
                user.setLastOnlineDate();
                Event.writeEvent("Вы оставили свое предложение на заказ ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/aof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOffer(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new OfferRequest(user.getPersonID(), offerID);
                user.setLastOnlineDate();
                Event.writeEvent("Вы оставили запрос на сделку ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    //заключение сделки(вы создатель вам предложили)
    @POST
    @Path("/cofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closeOfferRequest(@FormParam("token") String token,
                                          @FormParam("personID") int id,
                                          @FormParam("date") String date,
                                          @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                OfferRequest request = OfferRequest.getRequestByID(requestID);
                PackageOffer offer = PackageOffer.getOfferByID(request.getOfferID());
                new Package(request.getPersonID(), offer.getPersonID(), offer.getSource(), offer.getDestination(), offer.getStartDate(), offer.getEndDate(), offer.getText(), offer.getLength(), null);
                PackageOffer.deletePackageOffer(offer.getOfferID());
                OfferRequest.deleteRequest(requestID);
                user.setLastOnlineDate();
                Event.writeEvent("Вы согласились на совершение сделки ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/corr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closeOrderRequest(@FormParam("token") String token,
                                          @FormParam("personID") int id,
                                          @FormParam("date") String date,
                                          @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                OrderRequest request = OrderRequest.getRequestByID(requestID);
                PackageOrder order = PackageOrder.getOrderByID(request.getOrderID());

                new Package(order.getPersonID(), request.getPersonID(), order.getSource(), order.getDestination(), order.getStartDate(), order.getEndDate(), order.getText(), order.getLength(), null);

                PackageOrder.deletePackageOrder(order.getOrderID(), user.getPersonID());
                OrderRequest.deleteRequest(requestID);


                user.setLastOnlineDate();

                Event.writeEvent("Вы согласились на совершение сделки ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (
                Exception ex)

        {
            return new DefaultClass(false, ex.getMessage());
        }

    }

    @POST
    @Path("/me/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeEvent(@FormParam("token") String token,
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("packageID") int packageID,
                                  @FormParam("event") String event) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(1, packageID);
                Package.setEvent(Service.dateFromString(event), packageID);


                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/ae/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptEvent(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(2, packageID);

                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/de/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass declineEvent(@FormParam("token") String token,
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package _package = Package.getPackageByID(packageID);

                new PackageOrder(_package.getConsumerID(), _package.getSource(), _package.getDestination(), _package.getStartDate(), _package.getEndDate(), _package.getText(), _package.getLength());
                Package.deletePackage(packageID, user.getPersonID(), _package.getConsumerID());


                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/tp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass transferPackage(@FormParam("token") String token,
                                        @FormParam("personID") int id,
                                        @FormParam("date") String date,
                                        @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(3, packageID);
                Package.updateStatus(1, packageID, user.getPersonID());


                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/pf/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass proofFinish(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(4, packageID);


                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/cp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closePackage(@FormParam("token") String token,
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(5, packageID);


                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deletePackage(@FormParam("token") String token,
                                      @FormParam("personID") int id,
                                      @FormParam("date") String date,
                                      @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package _package = Package.getPackageByID(packageID);

                Package.deletePackage(packageID, user.getPersonID(), _package.getProducerID());


                user.setLastOnlineDate();
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/gofi/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PackageOffer getOfferInfo(@FormParam("token") String token,
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                PackageOffer offer = PackageOffer.getOfferByID(offerID);
                offer.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return offer;
            }
            throw new Exception("token error");

        } catch (Exception ex) {
            PackageOffer offer = new PackageOffer();
            offer.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offer;
        }
    }

    @POST
    @Path("/gori/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PackageOrder getOrderInfo(@FormParam("token") String token,
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                PackageOrder order = PackageOrder.getOrderByID(orderID);
                order.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return order;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            PackageOrder order = new PackageOrder();
            order.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return order;
        }
    }

    @POST
    @Path("/gpi/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Package getPackageInfo(@FormParam("token") String token,
                                  @FormParam("personID") int id,
                                  @FormParam("date") String date,
                                  @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Package _package = Package.getPackageByID(packageID);
                _package.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return _package;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Package _package = new Package();
            _package.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return _package;
        }
    }

    @POST
    @Path("/gofby/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getOffersByParams(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("source") String source,
                                    @FormParam("destination") String destination,
                                    @FormParam("startDate") String startDate,
                                    @FormParam("endDate") String endDate) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Offers offers = Offers.getOffersByParams(source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate));
                offers.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return offers;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    @POST
    @Path("/gorby/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getOrdersByParams(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date,
                                    @FormParam("source") String source,
                                    @FormParam("destination") String destination,
                                    @FormParam("startDate") String startDate,
                                    @FormParam("endDate") String endDate) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Orders orders = Orders.getOrdersByParams(source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate));
                orders.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return orders;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    @POST
    @Path("/guor/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getUserOrders(@FormParam("token") String token,
                                @FormParam("personID") int id,
                                @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Orders orders = Orders.getOrdersByID(user.getPersonID());
                orders.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return orders;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    @POST
    @Path("/guof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getUserOffers(@FormParam("token") String token,
                                @FormParam("personID") int id,
                                @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Offers offers = Offers.getOffersByID(user.getPersonID());
                offers.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return offers;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    @POST
    @Path("/gup/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Packages getUserPackages(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Packages packages = Packages.getPackagesByUserID(user.getPersonID());
                packages.setDefaultClass(new DefaultClass(true, token));

                user.setLastOnlineDate();
                return packages;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Packages packages = new Packages();
            packages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return packages;
        }
    }

    @POST
    @Path("/gur/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Requests getUserRequests(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Requests requests = Requests.getRequestsByPersonID(user.getPersonID());

                user.setLastOnlineDate();
                requests.setDefaultClass(new DefaultClass(true, token));
                return requests;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Requests requests = new Requests();
            requests.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return requests;
        }
    }
}
