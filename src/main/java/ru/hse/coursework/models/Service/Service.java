package ru.hse.coursework.models.Service;

import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Packages.Offer.OfferRequest;
import ru.hse.coursework.models.Packages.Offer.Offers;
import ru.hse.coursework.models.Packages.Offer.PackageOffer;
import ru.hse.coursework.models.Packages.Order.OrderRequest;
import ru.hse.coursework.models.Packages.Order.Orders;
import ru.hse.coursework.models.Packages.Order.PackageOrder;
import ru.hse.coursework.models.Packages.Package;
import ru.hse.coursework.models.Packages.Packages;
import ru.hse.coursework.models.Response.Comment;
import ru.hse.coursework.models.Response.Response;
import ru.hse.coursework.models.User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Service {
    private static String url = "jdbc:sqlserver://coursew.database.windows.net:1433;database=CourseWID;user=HSEadmin@coursew;password=hsepassword16);encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    public static String makeToken(String login) {
        return login + getNowMomentInUTC();
    }

    public static void execCommand(String command) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();

        if (connection == null) {
            throw new Exception("Database Connection Error");
        }

        if (!statement.execute(command)) {
            throw new Exception("Database Query Error");
        }
    }

    public static Date dateFromString(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }

    public static Date getNowMomentInUTC() {
        return null;
    }

    public static User getUserByQuery(String query) throws Exception {
        User user = new User();

        ResultSet resultSet = getSelectResultSet(query);

        if (resultSet != null) {
            while (resultSet.next()) {
                user = new User(resultSet.getInt("personID"),
                        resultSet.getInt("countOfOrders"),
                        resultSet.getInt("countOfOffers"),
                        resultSet.getInt("experience"),
                        resultSet.getInt("rank"),
                        resultSet.getDate("lastOnlineDate"),
                        resultSet.getString("login"),
                        resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("HashPassword"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Token"));
            }
        }

        return user;
    }

    public static ArrayList<Response> getResponsesByQuery(String query) throws Exception {
        ArrayList<Response> responses = new ArrayList<Response>();
        ResultSet resultSet = getSelectResultSet(query);

        if (resultSet != null) {
            while (resultSet.next()) {
                Response response = new Response();
                response.setResponseID(resultSet.getInt("RequestID"));
                response.setComments(Comment.getCommentsByResponseID(response.getResponseID()));
                response.setPersonID(resultSet.getInt("PersonID"));
                response.setCriticID(resultSet.getInt("CriticID"));
                response.setCritic(User.getUserByID(response.getCriticID()));
                response.getCritic().clear();
                response.setMark(resultSet.getInt("Mark"));
                response.setText(resultSet.getString("Text"));
                response.setDate(resultSet.getDate("Date"));
                responses.add(response);
            }
        }
        return responses;
    }

    public static Response getResponseByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Response response = new Response();

        if (resultSet != null) {
            while (resultSet.next()) {
                response.setResponseID(resultSet.getInt("RequestID"));
                response.setComments(Comment.getCommentsByResponseID(response.getResponseID()));
                response.setPersonID(resultSet.getInt("PersonID"));
                response.setCriticID(resultSet.getInt("CriticID"));
                response.setCritic(User.getUserByID(response.getCriticID()));
                response.getCritic().clear();
                response.setMark(resultSet.getInt("Mark"));
                response.setText(resultSet.getString("Text"));
                response.setDate(resultSet.getDate("Date"));
            }
        }
        return response;
    }

    public static ArrayList<Comment> getCommentsByQuery(String query) throws Exception {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        ResultSet resultSet = getSelectResultSet(query);

        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setPersonID(resultSet.getInt("PersonID"));
            comment.setText(resultSet.getString("Text"));
            comment.setCommentID(resultSet.getInt("CommentID"));
            comment.setResponseID(resultSet.getInt("ResponseID"));
            comment.setCommenter(User.getUserByID(comment.getPersonID()));
            comment.getCommenter().clear();
            comment.setDate(resultSet.getDate("Date"));
            comments.add(comment);
        }

        return comments;
    }

    public static Offers getOffersByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Offers offers = new Offers();
        ArrayList<PackageOffer> offerArrayList = new ArrayList<PackageOffer>();

        if (resultSet != null) {
            while (resultSet.next()) {
                PackageOffer offer = new PackageOffer();
                offer.setText(resultSet.getString("Text"));
                offer.setPersonID(resultSet.getInt("PersonID"));
                offer.setPerson(User.getUserByID(offer.getPersonID()));
                offer.getPerson().clear();
                offer.setDate(resultSet.getString("Date"));
                offer.setDestination(resultSet.getString("Destination"));
                offer.setSource(resultSet.getString("Source"));
                offer.setPublishDate(resultSet.getDate("PublishDate"));
                offerArrayList.add(offer);
            }
        }

        offers.setOffers(offerArrayList);
        return offers;
    }

    public static Orders getOrdersByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Orders orders = new Orders();
        ArrayList<PackageOrder> orderArrayList = new ArrayList<PackageOrder>();
        if (resultSet != null) {
            while (resultSet.next()) {
                PackageOrder order = new PackageOrder();
                order.setText(resultSet.getString("Text"));
                order.setPersonID(resultSet.getInt("PersonID"));
                order.setPerson(User.getUserByID(order.getPersonID()));
                order.getPerson().clear();
                order.setDate(resultSet.getString("Date"));
                order.setDestination(resultSet.getString("Destination"));
                order.setSource(resultSet.getString("Source"));
                order.setPublishDate(resultSet.getDate("PublishDate"));

                /*
                byte[] photoArray = resultSet.getBytes("Photo");
                BASE64Encoder encoder = new BASE64Encoder();
                order.setPhoto(encoder.encode(photoArray));
                */

                orderArrayList.add(order);
            }
        }
        orders.setOrders(orderArrayList);
        return orders;
    }

    public static Packages getPackagesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Packages packages = new Packages();
        ArrayList<Package> packageArrayList = new ArrayList<Package>();

        while (resultSet.next()) {
            Package _package = new Package();
            _package.setText(resultSet.getString("Text"));
            _package.setConsumerID(resultSet.getInt("ConsumerID"));
            _package.setDate(resultSet.getString("Date"));
            _package.setDestination(resultSet.getString("Destination"));
            _package.setPackageID(resultSet.getInt("PackageID"));
            _package.setProducerID(resultSet.getInt("ProducerID"));
            _package.setSource(resultSet.getString("Source"));
            _package.setProducer(User.getUserByID(_package.getProducerID()));
            _package.setConsumer(User.getUserByID(_package.getConsumerID()));
            _package.getConsumer().clear();
            _package.getProducer().clear();
            packageArrayList.add(_package);
        }

        packages.setPackages(packageArrayList);
        return packages;
    }

    public static Message getMessageByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Message message = new Message();

        if (resultSet != null) {
            while (resultSet.next()) {
                message.setPersonID(resultSet.getInt("PersonID"));
                message.setText(resultSet.getString("Text"));
                message.setDate(resultSet.getDate("Date"));
                message.setDialogID(resultSet.getInt("DialogID"));
                message.setMessageID(resultSet.getInt("MessageID"));
            }
        }

        return message;
    }

    public static Dialog getDialogByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Dialog dialog = new Dialog();

        if (resultSet != null) {
            while (resultSet.next()) {
                dialog.setDialogID(resultSet.getInt("DialogID"));
                dialog.setMessages(Message.getMessagesByDialogID(dialog.getDialogID()));
                dialog.setPersonID_1(resultSet.getInt("PersonID_1"));
                dialog.setPersonID_2(resultSet.getInt("PersonID_2"));
            }
        }
        return dialog;
    }

    public static ArrayList<Message> getMessagesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<Message> messages = new ArrayList<Message>();

        while (resultSet.next()) {
            Message message = new Message();
            message.setPersonID(resultSet.getInt("PersonID"));
            message.setText(resultSet.getString("Text"));
            message.setDate(resultSet.getDate("Date"));
            message.setDialogID(resultSet.getInt("DialogID"));
            message.setMessageID(resultSet.getInt("MessageID"));
            messages.add(message);
        }

        return messages;
    }

    public static PackageOrder getOrderByQuery(String query) throws Exception {

        ResultSet resultSet = getSelectResultSet(query);
        PackageOrder order = new PackageOrder();

        if (resultSet != null) {
            while (resultSet.next()) {
                order.setText(resultSet.getString("Text"));
                order.setPersonID(resultSet.getInt("PersonID"));
                order.setPerson(User.getUserByID(order.getPersonID()));
                order.getPerson().clear();
                order.setOrderID(resultSet.getInt("OrderID"));
                order.setRequests(OrderRequest.getRequestsByOrderID(order.getOrderID()));
                order.setDate(resultSet.getString("Date"));
                order.setDestination(resultSet.getString("Destination"));
                order.setSource(resultSet.getString("Source"));
                order.setPublishDate(resultSet.getDate("PublishDate"));
            }
        }
        return order;
    }

    public static PackageOffer getOfferByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        PackageOffer offer = new PackageOffer();

        if (resultSet != null) {
            while (resultSet.next()) {
                offer.setText(resultSet.getString("Text"));
                offer.setPersonID(resultSet.getInt("PersonID"));
                offer.setPerson(User.getUserByID(offer.getPersonID()));
                offer.setDate(resultSet.getString("Date"));
                offer.setDestination(resultSet.getString("Destination"));
                offer.setSource(resultSet.getString("Source"));
                offer.setOfferID(resultSet.getInt("OfferID"));
                offer.setPublishDate(resultSet.getDate("PublishDate"));
                offer.setRequests(OfferRequest.getRequestsByOfferID(offer.getOfferID()));
                offer.getPerson().clear();
            }
        }

        return offer;
    }

    public static Package getPackageByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Package _package = new Package();

        while (resultSet.next()) {
            _package.setText(resultSet.getString("Text"));
            _package.setConsumerID(resultSet.getInt("ConsumerID"));
            _package.setDate(resultSet.getString("Date"));
            _package.setDestination(resultSet.getString("Destination"));
            _package.setPackageID(resultSet.getInt("PackageID"));
            _package.setProducerID(resultSet.getInt("ProducerID"));
            _package.setSource(resultSet.getString("Source"));
            _package.setProducer(User.getUserByID(_package.getProducerID()));
            _package.setConsumer(User.getUserByID(_package.getConsumerID()));
            _package.getConsumer().clear();
            _package.getProducer().clear();
        }
        return _package;
    }

    public static ArrayList<OfferRequest> getOfferRequestsByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<OfferRequest> requests = new ArrayList<OfferRequest>();

        if (resultSet != null) {
            while (resultSet.next()) {
                OfferRequest request = new OfferRequest();
                request.setRequestID(resultSet.getInt("RequestID"));
                request.setPersonID(resultSet.getInt("PersonID"));
                request.setOfferID(resultSet.getInt("OfferID"));
                request.setPerson(User.getUserByID(request.getPersonID()));
                request.getPerson().clear();
                requests.add(request);
            }
        }

        return requests;
    }

    public static ArrayList<OrderRequest> getOrderRequestsByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<OrderRequest> requests = new ArrayList<OrderRequest>();

        if (resultSet != null) {
            while (resultSet.next()) {
                OrderRequest request = new OrderRequest();
                request.setRequestID(resultSet.getInt("RequestID"));
                request.setPersonID(resultSet.getInt("PersonID"));
                request.setOrderID(resultSet.getInt("OrderID"));
                request.setPerson(User.getUserByID(request.getPersonID()));
                request.getPerson().clear();
                requests.add(request);
            }
        }

        return requests;
    }

    public static OfferRequest getOfferRequestByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        OfferRequest request = new OfferRequest();

        if (resultSet != null) {
            while (resultSet.next()) {
                request.setRequestID(resultSet.getInt("RequestID"));
                request.setPersonID(resultSet.getInt("PersonID"));
                request.setOfferID(resultSet.getInt("OfferID"));
                request.setPerson(User.getUserByID(request.getPersonID()));
                request.getPerson().clear();
            }
        }

        return request;
    }

    public static OrderRequest getOrderRequestByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        OrderRequest request = new OrderRequest();

        while (resultSet.next()) {
            request.setRequestID(resultSet.getInt("RequestID"));
            request.setPersonID(resultSet.getInt("PersonID"));
            request.setOrderID(resultSet.getInt("OrderID"));
            request.setPerson(User.getUserByID(request.getPersonID()));
            request.getPerson().clear();
        }

        return request;
    }

    private static ResultSet getSelectResultSet(String query) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(query);
        } catch (Exception ex) {
            throw new Exception("Database Query Error");
        }
        return resultSet;
    }
}
