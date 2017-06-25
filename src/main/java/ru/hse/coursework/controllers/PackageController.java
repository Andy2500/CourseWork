package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.Packages.Offer.OfferRequest;
import ru.hse.coursework.models.Packages.Offer.Offers;
import ru.hse.coursework.models.Packages.Offer.PackageOffer;
import ru.hse.coursework.models.Packages.Order.OrderRequest;
import ru.hse.coursework.models.Packages.Order.Orders;
import ru.hse.coursework.models.Packages.Order.PackageOrder;
import ru.hse.coursework.models.Packages.Package;
import ru.hse.coursework.models.Packages.Packages;
import ru.hse.coursework.models.User.User;
import ru.hse.coursework.service.DateWorker;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

import static ru.hse.coursework.service.DateWorker.dateFromString;

@Path("/pack")
public class PackageController {

    /**
     * Метод для создания предложения
     * Путь: /pack/mof
     *
     * @param token       - токен пользователя - токен пользователя
     * @param source      - пункт отправления
     * @param destination - пункт доставки
     * @param startDate   - дата отправления
     * @param endDate     - дата доставки
     * @param text        - подробное описание - подробное описание
     * @return DefaultClass
     */
    @POST
    @Path("/mof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeOffer(@FormParam("token") String token,
                                  @FormParam("source") String source,
                                  @FormParam("destination") String destination,
                                  @FormParam("startDate") String startDate,
                                  @FormParam("endDate") String endDate,
                                  @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            new PackageOffer(user.getPersonID(), source, destination, dateFromString(startDate), dateFromString(endDate), text);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для создания заказа
     * Путь: /pack/mor
     *
     * @param token       - токен пользователя
     * @param source      - пункт отправления
     * @param destination - пункт доставки
     * @param startDate   - дата отправления
     * @param endDate     - дата доставки
     * @param text        - подробное описание
     * @return DefaultClass
     */
    @POST
    @Path("/mor/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass makeOrder(@FormParam("token") String token,
                                  @FormParam("source") String source,
                                  @FormParam("destination") String destination,
                                  @FormParam("startDate") String startDate,
                                  @FormParam("endDate") String endDate,
                                  @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            new PackageOrder(user.getPersonID(), source, destination, dateFromString(startDate), dateFromString(endDate), text);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для удаления заказа
     * Путь: /pack/dor
     *
     * @param token   - токен пользователя
     * @param orderID - ID заказа
     * @return DefaultClass
     */
    @POST
    @Path("/dor/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOrderByID(@FormParam("token") String token,
                                        @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            PackageOrder.deletePackageOrder(orderID, user.getPersonID());
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для удаления предложения
     * Путь: /pack/dof
     *
     * @param token   - токен пользователя
     * @param offerID - ID предложения
     * @return DefaultClass
     */
    @POST
    @Path("/dof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOfferByID(@FormParam("token") String token,
                                        @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            PackageOffer.deletePackageOffer(offerID, user.getPersonID());
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }


    /**
     * Метод для создания запроса на исполнение
     * Путь: /pack/corr
     *
     * @param token   - токен пользователя
     * @param orderID - ID заказа
     * @return DefaultClass
     */
    @POST
    @Path("/corr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createOrderRequest(@FormParam("token") String token,
                                           @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            new OrderRequest(user.getPersonID(), orderID);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");


        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для удаление запроса на предложение
     * Путь: /pack/dofr
     *
     * @param token     - токен пользователя
     * @param requestID - ID запроса
     * @return DefaultClass
     */
    @POST
    @Path("/dofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOfferRequest(@FormParam("token") String token,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            OfferRequest.deleteRequest(requestID);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для удаления запроса на заказ
     * Путь: /pack/dorr
     *
     * @param token     - токен пользователя
     * @param requestID - ID запроса
     * @return DefaultClass
     */
    @POST
    @Path("/dorr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deleteOrderRequest(@FormParam("token") String token,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            OrderRequest.deleteRequest(requestID);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для создания запроса на предложение
     * Путь: /pack/cofr
     *
     * @param token   - токен пользователя
     * @param offerID - ID предложения
     * @return DefaultClass
     */
    @POST
    @Path("/cofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createOfferRequest(@FormParam("token") String token,
                                           @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            new OfferRequest(user.getPersonID(), offerID);
            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для одобрения запроса на предложение
     * Путь: /pack/aofr
     *
     * @param token     - токен пользователя
     * @param requestID - ID запроса
     * @return DefaultClass
     */

    @POST
    @Path("/aofr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOfferRequest(@FormParam("token") String token,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            OfferRequest request = OfferRequest.getRequestByID(requestID);
            PackageOffer offer = PackageOffer.getOfferByID(request.getOfferID());
            new Package(offer, request.getPersonID());

            PackageOffer.deletePackageOffer(offer.getOfferID(), user.getPersonID());
            OfferRequest.deleteAllRequestsWithOutRequestID(offer.getOfferID(), requestID);

            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для одобрения запроса на заказ
     * Путь: /pack/aorr
     *
     * @param token     - токен пользователя
     * @param requestID - ID запроса
     * @return DefaultClass - где:
     * operationOutput - правильно ли прошла операция,
     * token - либо сообщение об ошибки, либо ""
     */
    @POST
    @Path("/aorr/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass acceptOrderRequest(@FormParam("token") String token,
                                           @FormParam("requestID") int requestID) {
        try {
            User user = User.getUserByToken(token, false, false, false);

            OrderRequest request = OrderRequest.getRequestByID(requestID);
            PackageOrder order = PackageOrder.getOrderByID(request.getOrderID());
            PackageOrder.deletePackageOrder(order.getOrderID(), user.getPersonID());
            OrderRequest.deleteRequest(requestID);

            User.setLastOnlineDate(user.getPersonID());

            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }

    }


    /**
     * Метод для создания сделки
     * Путь: /pack/cp
     *
     * @param token                - токен пользователя
     * @param consumerID           - ID заказчика
     * @param producerID           - ID исполнителя
     * @param getterID             - ID получателя
     * @param sourceLatitude       - Широта места встречи
     * @param sourceLongitude      - Долгота места встречи
     * @param destinationLatitude  - Широта места доставки
     * @param destinationLongitude - Долгота места доставки
     * @param sourceAddress        - Адрес места встречи
     * @param destinationAddress   - Адрес места доставки
     * @param eventDate            - Дата встречи
     * @param finishDate           - Дата доставки
     * @param text                 - подробное описание
     * @return DefaultClass
     */
    @POST
    @Path("/cp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createPackage(@FormParam("token") String token,
                                      @FormParam("consumerID") int consumerID,
                                      @FormParam("producerID") int producerID,
                                      @FormParam("getterID") int getterID,
                                      @FormParam("sourceLatitude") float sourceLatitude,
                                      @FormParam("sourceLongitude") float sourceLongitude,
                                      @FormParam("destinationLatitude") float destinationLatitude,
                                      @FormParam("destinationLongitude") float destinationLongitude,
                                      @FormParam("sourceAddress") String sourceAddress,
                                      @FormParam("destinationAddress") String destinationAddress,
                                      @FormParam("eventDate") String eventDate,
                                      @FormParam("finishDate") String finishDate,
                                      @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            sourceAddress = sourceAddress.replace("\'", "\\'");
            destinationAddress = destinationAddress.replace("\'", "\\'");
            new Package(consumerID, producerID, getterID, sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude, sourceAddress, destinationAddress, dateFromString(eventDate), dateFromString(finishDate));

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для настройки сделки
     * Путь: /pack/rcp
     *
     * @param token                - токен пользователя
     * @param consumerID           - ID заказчика
     * @param producerID           - ID исполнителя
     * @param getterID             - ID получателя
     * @param sourceLatitude       - Широта места встречи
     * @param sourceLongitude      - Долгота места встречи
     * @param destinationLatitude  - Широта места доставки
     * @param destinationLongitude - Долгота места доставки
     * @param sourceAddress        - Адрес места встречи
     * @param destinationAddress   - Адрес места доставки
     * @param eventDate            - Дата встречи
     * @param finishDate           - Дата доставки
     * @param lastPackageID        - ID Сделки, которую настроили
     * @return DefaultClass
     */
    @POST
    @Path("/rcp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass recreatePackage(@FormParam("token") String token,
                                        @FormParam("consumerID") int consumerID,
                                        @FormParam("producerID") int producerID,
                                        @FormParam("getterID") int getterID,
                                        @FormParam("sourceLatitude") float sourceLatitude,
                                        @FormParam("sourceLongitude") float sourceLongitude,
                                        @FormParam("destinationLatitude") float destinationLatitude,
                                        @FormParam("destinationLongitude") float destinationLongitude,
                                        @FormParam("sourceAddress") String sourceAddress,
                                        @FormParam("destinationAddress") String destinationAddress,
                                        @FormParam("eventDate") String eventDate,
                                        @FormParam("finishDate") String finishDate,
                                        @FormParam("lastPackageID") int lastPackageID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            sourceAddress = sourceAddress.replace("\'", "\\'");
            destinationAddress = destinationAddress.replace("\'", "\\'");
            Package.deletePackage(lastPackageID);
            new Package(consumerID, producerID, getterID, sourceLatitude, sourceLongitude, destinationLatitude, destinationLongitude, sourceAddress, destinationAddress, dateFromString(eventDate), dateFromString(finishDate));

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для удаления сделки
     * Путь: /pack/dp
     *
     * @param token     - токен пользователя
     * @param packageID - ID сделки
     * @return DefaultClass - где:
     */
    @POST
    @Path("/dp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass deletePackage(@FormParam("token") String token,
                                      @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token, false, false, false);

            Package.deletePackage(packageID);

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для подтверждения передачи посылки
     * Путь: /pack/tp
     *
     * @param token      - токен пользователя
     * @param packageID  - ID сделки
     * @param photoProof - подтверждение(фото) передачи посылки в формате base64
     * @return DefaultClass
     */
    @POST
    @Path("/tp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass transferPackage(@FormParam("token") String token,
                                        @FormParam("packageID") int packageID,
                                        @FormParam("photoProof") String photoProof) {
        try {
            User user = User.getUserByToken(token, false, false, false);

            Package.setStatus(1, packageID);
            Package.setTransferProof(packageID, photoProof);

            User.setStatus(Package.getProducerIDByPackageID(packageID), 2);
            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для подтверждения доставки посылки
     * Путь: /pack/pd
     *
     * @param token      - токен пользователя
     * @param packageID  - ID сделки
     * @param photoProof - подтверждение(фото) доставки в формате base64
     * @return DefaultClass
     */
    @POST
    @Path("/pd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass proofDelivery(@FormParam("token") String token,
                                      @FormParam("packageID") int packageID,
                                      @FormParam("photoProof") String photoProof) {
        try {
            User user = User.getUserByToken(token, false, false, false);

            Package.setStatus(2, packageID);
            Package.setDeliveryProof(packageID, photoProof);

            if (User.getCountOfOpenProducerPackagesByPersonID(user.getPersonID()) == 0) {
                User.setStatus(user.getPersonID(), 1);
            }

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для закрытия сделки
     * Путь: /pack/clp
     *
     * @param token     - токен пользователя
     * @param packageID - ID сделки
     * @return DefaultClass
     */
    @POST
    @Path("/clp/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closePackage(@FormParam("token") String token,
                                     @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token, false, false, false);

            Package.setStatus(3, packageID);

            User.setLastOnlineDate(user.getPersonID());
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getMessage());
        }
    }

    /**
     * Метод для получения информации о предложении
     * Путь: /pack/gofi
     *
     * @param token   - токен пользователя
     * @param offerID - ID предложения
     */
    @POST
    @Path("/gofi/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PackageOffer getOfferInfo(@FormParam("token") String token,
                                     @FormParam("offerID") int offerID) {
        try {
            User user = User.getUserByToken(token, false, false, false);

            PackageOffer offer = PackageOffer.getOfferByID(offerID);
            offer.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return offer;


        } catch (Exception ex) {
            PackageOffer offer = new PackageOffer();
            offer.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offer;
        }
    }

    /**
     * Метод для получения информации о заказе
     * Путь: /pack/gori
     *
     * @param token   - токен пользователя
     * @param orderID - ID заказа
     */
    @POST
    @Path("/gori/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public PackageOrder getOrderInfo(@FormParam("token") String token,
                                     @FormParam("orderID") int orderID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            PackageOrder order = PackageOrder.getOrderByID(orderID);
            order.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return order;

        } catch (Exception ex) {
            PackageOrder order = new PackageOrder();
            order.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return order;
        }
    }

    /**
     * Метод для получения информации о сделке
     * Путь: /pack/gpi
     *
     * @param token     - токен пользователя
     * @param packageID - ID сделки
     */
    @POST
    @Path("/gpi/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Package getPackageInfo(@FormParam("token") String token,
                                  @FormParam("packageID") int packageID) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            Package _package = Package.getPackageByID(packageID);
            _package.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return _package;

        } catch (Exception ex) {
            Package _package = new Package();
            _package.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return _package;
        }
    }

    /**
     * Метод для поиска предложений
     * Путь: /pack/gofby
     *
     * @param token       - токен пользователя
     * @param source      - пункт отправления
     * @param destination - пункт доставки
     * @param startDate   - дата отправления
     * @param endDate     - дата доставки
     */
    @POST
    @Path("/gofby/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getOffersByParams(@FormParam("token") String token,
                                    @FormParam("source") String source,
                                    @FormParam("destination") String destination,
                                    @FormParam("startDate") String startDate,
                                    @FormParam("endDate") String endDate) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            source = source.replace("\'", "\\'");
            Offers offers = Offers.getOffersByParams(source, destination, dateFromString(startDate), dateFromString(endDate));
            offers.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return offers;

        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    /**
     * Метод для поиска заказов
     * Путь: /pack/gorby
     *
     * @param token       - токен пользователя
     * @param source      - пункт отправления
     * @param destination - пункт доставки
     * @param startDate   - дата отправления
     * @param endDate     - дата доставки
     */
    @POST
    @Path("/gorby/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getOrdersByParams(@FormParam("token") String token,
                                    @FormParam("source") String source,
                                    @FormParam("destination") String destination,
                                    @FormParam("startDate") String startDate,
                                    @FormParam("endDate") String endDate) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            source = source.replace("\'", "\\'");
            destination = destination.replace("\'", "\\'");
            Orders orders = Orders.getOrdersByParams(source, destination, DateWorker.dateFromString(startDate), DateWorker.dateFromString(endDate));
            orders.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return orders;

        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    /**
     * Метод для получения заказов пользователя
     * Путь: /pack/guor
     *
     * @param token - токен пользователя
     */
    @POST
    @Path("/guor/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getUserOrders(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            Orders orders = Orders.getOrdersByUserID(user.getPersonID());
            orders.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return orders;

        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    /**
     * Метод для получения предожений пользователя
     * Путь: /pack/guof
     *
     * @param token - токен пользователя
     */
    @POST
    @Path("/guof/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getUserOffers(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            Offers offers = Offers.getOffersByUserID(user.getPersonID());
            offers.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return offers;

        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }

    /**
     * Метод для получения сделок пользователя
     * Путь: /pack/gup
     *
     * @param token - токен пользователя
     */
    @POST
    @Path("/gup/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Packages getUserPackages(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            Packages packages = Packages.getPackagesByUserID(user.getPersonID());
            packages.setDefaultClass(new DefaultClass(true, ""));

            User.setLastOnlineDate(user.getPersonID());
            return packages;

        } catch (Exception ex) {
            Packages packages = new Packages();
            packages.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return packages;
        }
    }

    /**
     * Метод для получения заказов, на которые были оставлены запросы
     * Путь: /pack/gorbur
     *
     * @param token - токен пользователя
     */
    @POST
    @Path("/gorbur/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Orders getOrdersByUserRequest(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            ArrayList<OrderRequest> requests = OrderRequest.getRequestsByPersonID(user.getPersonID());
            if (requests.size() > 0) {
                Orders orders = PackageOrder.getOrdersByRequests(requests);
                orders.setDefaultClass(new DefaultClass(true, ""));
                User.setLastOnlineDate(user.getPersonID());
                return orders;
            }
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(true, ""));

            return orders;

        } catch (Exception ex) {
            Orders orders = new Orders();
            orders.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return orders;
        }
    }

    /**
     * Метод для получения предложений, на которые были оставлены запросы
     * Путь: /pack/gofbur/
     *
     * @param token - токен пользователя
     * @return массив сущностей Offer и Default Class
     */
    @POST
    @Path("/gofbur/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Offers getOffersByUserRequest(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token, false, false, false);
            ArrayList<OfferRequest> requests = OfferRequest.getRequestsByPersonID(user.getPersonID());
            if (requests.size() > 0) {
                Offers offers = PackageOffer.getOffersByRequests(requests);
                offers.setDefaultClass(new DefaultClass(true, ""));
                User.setLastOnlineDate(user.getPersonID());
                return offers;
            }
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(true, ""));

            return offers;

        } catch (Exception ex) {
            Offers offers = new Offers();
            offers.setDefaultClass(new DefaultClass(false, ex.getMessage()));
            return offers;
        }
    }
}