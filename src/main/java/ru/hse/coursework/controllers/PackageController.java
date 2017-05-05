package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Event.Event;
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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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
                User.setLastOnlineDate(id);
                Event.writeEvent("Вы создали заказ: " + source + "->" + destination, user.getPersonID());
                return new DefaultClass(true, "");
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
                User.setLastOnlineDate(id);
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
                User.setLastOnlineDate(id);
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
                PackageOffer.deletePackageOffer(offerID, id);
                User.setLastOnlineDate(id);
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
    @Path("/corr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createOrderRequest(@FormParam("token") String token,
                                           @FormParam("personID") int id,
                                           @FormParam("date") String date,
                                           @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new OrderRequest(user.getPersonID(), orderID);
                User.setLastOnlineDate(id);
                Event.writeEvent("Вы оставили свое предложение на заказ ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOfferRequest(@FormParam("token") String token,
                                           @FormParam("personID") int id,
                                           @FormParam("date") String date,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                OfferRequest.deleteRequest(requestID);
                User.setLastOnlineDate(id);
                Event.writeEvent("Вы удалили свой запрос на сделку ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/dorr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOrderRequest(@FormParam("token") String token,
                                           @FormParam("personID") int id,
                                           @FormParam("date") String date,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                OrderRequest.deleteRequest(requestID);
                User.setLastOnlineDate(id);
                Event.writeEvent("Вы удалили свое предложение на заказ ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/cofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createOfferRequest(@FormParam("token") String token,
                                           @FormParam("personID") int id,
                                           @FormParam("date") String date,
                                           @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new OfferRequest(user.getPersonID(), offerID);
                User.setLastOnlineDate(id);
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
    @Path("/aofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOfferRequest(@FormParam("token") String token,
                                           @FormParam("personID") int id,
                                           @FormParam("date") String date,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                OfferRequest request = OfferRequest.getRequestByID(requestID);
                PackageOffer offer = PackageOffer.getOfferByID(request.getOfferID());
                new Package(offer, request.getPersonID());

                PackageOffer.deletePackageOffer(offer.getOfferID(), id);
                OfferRequest.deleteAllRequestsWithOutRequestID(offer.getOfferID(), requestID);

                User.setLastOnlineDate(id);
                Event.writeEvent("Вы согласились на совершение сделки ", user.getPersonID());
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/aorr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOrderRequest(@FormParam("token") String token,
                                           @FormParam("personID") int id,
                                           @FormParam("date") String date,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                OrderRequest request = OrderRequest.getRequestByID(requestID);
                PackageOrder order = PackageOrder.getOrderByID(request.getOrderID());
                PackageOrder.deletePackageOrder(order.getOrderID(), user.getPersonID());
                OrderRequest.deleteRequest(requestID);

                User.setLastOnlineDate(id);

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


    /**
     * Создание сделки
     */
    @POST
    @Path("/cp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createPackage(@FormParam("token") String token,
                                      @FormParam("personID") int id,
                                      @FormParam("date") String date,
                                      @FormParam("consumerID") int consumerID,
                                      @FormParam("producerID") int producerID,
                                      @FormParam("getterID") int getterID,
                                      @FormParam("sourceLatitude") float sourceLatitude,
                                      @FormParam("sourceLongitude") float sourceLongitude,
                                      @FormParam("destinationLatitude") float destinationLatitude,
                                      @FormParam("destinationLongitude") float destinationLongitude,
                                      @FormParam("sourceAdress") String sourceAdress,
                                      @FormParam("destinationAdress") String destinationAdress,
                                      @FormParam("eventDate") String eventDate,
                                      @FormParam("finishDate") String finishDate,
                                      @FormParam("text") String text) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                sourceAdress = sourceAdress.replace("\'", "\\'");
                destinationAdress = destinationAdress.replace("\'", "\\'");
                new Package(consumerID, producerID, getterID, sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude, sourceAdress, destinationAdress, Service.dateFromString(eventDate), Service.dateFromString(finishDate), text);

                User.setLastOnlineDate(id);
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Создание сделки
     */
    @POST
    @Path("/rcp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass recreatePackage(@FormParam("token") String token,
                                        @FormParam("personID") int id,
                                        @FormParam("date") String date,
                                        @FormParam("consumerID") int consumerID,
                                        @FormParam("producerID") int producerID,
                                        @FormParam("getterID") int getterID,
                                        @FormParam("sourceLatitude") float sourceLatitude,
                                        @FormParam("sourceLongitude") float sourceLongitude,
                                        @FormParam("destinationLatitude") float destinationLatitude,
                                        @FormParam("destinationLongitude") float destinationLongitude,
                                        @FormParam("sourceAdress") String sourceAdress,
                                        @FormParam("destinationAdress") String destinationAdress,
                                        @FormParam("eventDate") String eventDate,
                                        @FormParam("finishDate") String finishDate,
                                        @FormParam("text") String text,
                                        @FormParam("lastPackageID") int lastPackageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                sourceAdress = sourceAdress.replace("\'", "\\'");
                destinationAdress = destinationAdress.replace("\'", "\\'");
                Package.deletePackage(lastPackageID, consumerID, producerID);
                new Package(consumerID, producerID, getterID, sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude, sourceAdress, destinationAdress, Service.dateFromString(eventDate), Service.dateFromString(finishDate), text);

                User.setLastOnlineDate(id);
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

                Package.deletePackage(packageID, user.getPersonID(), _package.getConsumerID());

                User.setLastOnlineDate(id);
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
                                        @FormParam("packageID") int packageID,
                                        @FormParam("photoProof") String photoProof) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(1, packageID);
                Package.setTransferProof(packageID, photoProof);

                User.setStatus(Package.getProducerIDByPackageID(packageID), 2);
                User.setLastOnlineDate(id);
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/pd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass proofDelivery(@FormParam("token") String token,
                                      @FormParam("personID") int id,
                                      @FormParam("date") String date,
                                      @FormParam("packageID") int packageID,
                                      @FormParam("photoProof") String photoProof) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(2, packageID);
                Package.setDeliveryProof(packageID, photoProof);

                if (User.getCountOfOpenProducerPackagesByPersonID(id) == 0) {
                    User.setStatus(id, 1);
                }

                User.setLastOnlineDate(id);
                return new DefaultClass(true, "");
            }

            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    @POST
    @Path("/clp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closePackage(@FormParam("token") String token,
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {

                Package.setStatus(3, packageID);

                User.setLastOnlineDate(id);
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
                offer.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                order.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                _package.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                source = source.replace("\'", "\\'");
                Offers offers = Offers.getOffersByParams(source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate));
                offers.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                source = source.replace("\'", "\\'");
                destination = destination.replace("\'", "\\'");
                Orders orders = Orders.getOrdersByParams(source, destination, Service.dateFromString(startDate), Service.dateFromString(endDate));
                orders.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                Orders orders = Orders.getOrdersByUserID(user.getPersonID());
                orders.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                Offers offers = Offers.getOffersByUserID(user.getPersonID());
                offers.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
                packages.setDefaultClass(new DefaultClass(true, ""));

                User.setLastOnlineDate(id);
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
    @Path("/gorbur/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getOrdersByUserRequest(@FormParam("token") String token,
                                         @FormParam("personID") int id,
                                         @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                ArrayList<OrderRequest> requests = OrderRequest.getRequestsByPersonID(id);
                if (requests.size() > 0) {
                    Orders orders = PackageOrder.getOrdersByRequests(requests);
                    orders.setDefaultClass(new DefaultClass(true, ""));
                    User.setLastOnlineDate(id);
                    return orders;
                }
                Orders orders = new Orders();
                orders.setDefaultClass(new DefaultClass(true, ""));

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
    @Path("/gofbur/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getOffersByUserRequest(@FormParam("token") String token,
                                         @FormParam("personID") int id,
                                         @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                ArrayList<OfferRequest> requests = OfferRequest.getRequestsByPersonID(id);
                if (requests.size() > 0) {
                    Offers offers = PackageOffer.getOffersByRequests(requests);
                    offers.setDefaultClass(new DefaultClass(true, ""));
                    User.setLastOnlineDate(id);
                    return offers;
                }
                Offers offers = new Offers();
                offers.setDefaultClass(new DefaultClass(true, ""));

                return offers;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }
/*
 * Получить список заказов и предложений, где пользователь оставил запрос
 */
}
