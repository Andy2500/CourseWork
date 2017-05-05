package ru.hse.coursework.models.Service;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Dispute.Dispute;
import ru.hse.coursework.models.Dispute.Disputes;
import ru.hse.coursework.models.Event.Event;
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
import ru.hse.coursework.models.User.Users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Service {

    public static String makeToken(String login) throws Exception {
// String str = login + Service.getNowMomentInUTC();
        String str = login;
        MessageDigest messageDigest;

        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(str.getBytes());
        byte[] digest = messageDigest.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    public static void execCommand(String command) throws Exception {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("coursew.database.windows.net");
        ds.setPortNumber(1433);
        ds.setDatabaseName("CourseWID");
        ds.setEncrypt(true);
        ds.setPassword("hsepassword16)");
        ds.setUser("HSEADMIN");
        ds.setHostNameInCertificate("*.database.windows.net");
        ds.setTrustServerCertificate(false);
        ds.setLoginTimeout(1000);

        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();

        statement.execute(command);
    }

    public static Date dateFromString(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
        String[] sp = date.split(":");
        if (sp.length == 4) {
            int l = sp[3].length();
            String nilstr = "";

            for (int i = l; i < 3; i++) {
                nilstr += "0";
            }

            sp[3] = sp[3] + nilstr;
            date = sp[0] + ":" + sp[1] + ":" + sp[2] + ":" + sp[3];
            return dateFormat.parse(date);
        } else {
            dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            return dateFormat.parse(date);
        }
    }

    public static String makeSqlDateString(Date date) {
        DateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        if (format.format(date).equals("00:00:00:000")) {
            return dateFormat.format(date);
        } else {
            return dateFormat.format(date) + " " + format.format(date);
        }
    }

    public static String getNowMomentInUTC() {
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        String date = calendar.get(Calendar.YEAR) + "."
                + (calendar.get(Calendar.MONTH) + 1) + "."
                + calendar.get(Calendar.DAY_OF_MONTH) + " "
                + calendar.get(Calendar.HOUR_OF_DAY) + ":"
                + calendar.get(Calendar.MINUTE) + ":"
                + calendar.get(Calendar.SECOND) + ":"
                + calendar.get(Calendar.MILLISECOND);
        return date;
    }

    public static User getUserByQuery(String query) throws Exception {
        User user = new User();

        ResultSet resultSet = getSelectResultSet(query);

        while (resultSet.next()) {
            byte[] ph = resultSet.getBytes("Photo");
            byte[] docPhoto = resultSet.getBytes("DocumentPhoto");

            user.setPersonID(resultSet.getInt("personID"));
            user.setLogin(resultSet.getString("login"));
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            user.setHashpsd(resultSet.getString("HashPassword"));
            user.setPhone(resultSet.getString("Phone"));
            user.setCountOfOffers(Service.getIntByQuery("Select Count(*) From Offers Where PersonID = " + user.getPersonID(), ""));
            user.setCountOfOrders(Service.getIntByQuery("Select Count(*) From Orders Where PersonID = " + user.getPersonID(), ""));
            user.setCountOfPackages(Service.getIntByQuery("Select Count(*) From Packages Where ConsumerID = " + user.getPersonID() + "OR ProducerID = " + user.getPersonID() + "OR GetterID = " + user.getPersonID(), ""));
            user.setCountOfResponses(Service.getIntByQuery("Select Count(*) From Responses Where PersonID = " + user.getPersonID(), ""));
            user.setSumOfResponses(Service.getIntByQuery("Select Sum(Mark) From Responses Where PersonID = " + user.getPersonID(), ""));
            user.setLastLatitude(resultSet.getFloat("LastLatitude"));
            user.setLastLongitude(resultSet.getFloat("LastLongitude"));
            user.setLastOnlineDate(resultSet.getTimestamp("lastOnlineDate"));
            user.setToken(resultSet.getString("Token"));
            user.setStatus(resultSet.getInt("Status"));

            if (ph != null) {
                user.setPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(ph));
            }
            if (docPhoto != null) {
                user.setPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(docPhoto));
            }
        }

        return user;
    }

    public static Users getUsersByQuery(String query) throws Exception {
        Users users = new Users();
        users.setUsers(new ArrayList<User>());

        ResultSet resultSet = getSelectResultSet(query);

        while (resultSet.next()) {
            User user = new User();
            byte[] ph = resultSet.getBytes("Photo");
            byte[] docPhoto = resultSet.getBytes("DocumentPhoto");

            user.setPersonID(resultSet.getInt("personID"));
            user.setLogin(resultSet.getString("login"));
            user.setEmail(resultSet.getString("email"));
            user.setName(resultSet.getString("name"));
            user.setHashpsd(resultSet.getString("HashPassword"));
            user.setPhone(resultSet.getString("Phone"));
            user.setCountOfOffers(Service.getIntByQuery("Select Count(*) From Offers Where PersonID = " + user.getPersonID(), ""));
            user.setCountOfOrders(Service.getIntByQuery("Select Count(*) From Orders Where PersonID = " + user.getPersonID(), ""));
            user.setCountOfPackages(Service.getIntByQuery("Select Count(*) From Packages Where ConsumerID = " + user.getPersonID() + "OR ProducerID = " + user.getPersonID() + "OR GetterID = " + user.getPersonID(), ""));
            user.setCountOfResponses(Service.getIntByQuery("Select Count(*) From Responses Where PersonID = " + user.getPersonID(), ""));
            user.setSumOfResponses(Service.getIntByQuery("Select Sum(Mark) From Responses Where PersonID = " + user.getPersonID(), ""));
            user.setLastLatitude(resultSet.getFloat("LastLatitude"));
            user.setLastLongitude(resultSet.getFloat("LastLongitude"));
            user.setLastOnlineDate(resultSet.getTimestamp("lastOnlineDate"));
            user.setToken(resultSet.getString("Token"));
            user.setStatus(resultSet.getInt("Status"));

            if (ph != null) {
                user.setPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(ph));
            }

            if (docPhoto != null) {
                user.setPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(docPhoto));
            }

            users.getUsers().add(user);
        }

        return users;
    }


    public static ArrayList<Response> getResponsesByQuery(String query) throws Exception {
        ArrayList<Response> responses = new ArrayList<Response>();
        ResultSet resultSet = getSelectResultSet(query);
        int i = 0;
        if (resultSet != null) {
            while (resultSet.next()) {
                i++;
                Response response = new Response();
                response.setResponseID(resultSet.getInt("ResponseID"));
                response.setComments(Comment.getCommentsByResponseID(response.getResponseID()));
                response.setPersonID(resultSet.getInt("PersonID"));
                response.setCriticID(resultSet.getInt("CriticID"));
                response.setCritic(User.getUserByID(response.getCriticID()));
                response.setMark(resultSet.getInt("Mark"));
                response.setText(resultSet.getString("Text"));
                response.setDate(resultSet.getTimestamp("Date"));

                response.getCritic().clear();
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
                response.setResponseID(resultSet.getInt("ResponseID"));
                response.setComments(Comment.getCommentsByResponseID(response.getResponseID()));
                response.setPersonID(resultSet.getInt("PersonID"));
                response.setCriticID(resultSet.getInt("CriticID"));
                response.setCritic(User.getUserByID(response.getCriticID()));
                response.setMark(resultSet.getInt("Mark"));
                response.setText(resultSet.getString("Text"));
                response.setDate(resultSet.getTimestamp("Date"));

                response.getCritic().clear();
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
            comment.setDate(resultSet.getTimestamp("Date"));

            comment.getCommenter().clear();
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

                offer.setOfferID(resultSet.getInt("OfferID"));
                offer.setText(resultSet.getString("Text"));
                offer.setPersonID(resultSet.getInt("PersonID"));
                offer.setStartDate(resultSet.getTimestamp("StartDate"));
                offer.setEndDate(resultSet.getTimestamp("EndDate"));
                offer.setDestination(resultSet.getString("Destination"));
                offer.setSource(resultSet.getString("Source"));
                offer.setPublishDate(resultSet.getTimestamp("PublishDate"));
                offer.setWatches(resultSet.getInt("Watches"));

                offer.setRequests(OfferRequest.getRequestsByOfferID(offer.getOfferID()));
                offer.setPerson(User.getUserByID(offer.getPersonID()));

                offer.getPerson().clear();
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

                order.setOrderID(resultSet.getInt("OrderID"));
                order.setText(resultSet.getString("Text"));
                order.setPersonID(resultSet.getInt("PersonID"));
                order.setStartDate(resultSet.getTimestamp("StartDate"));
                order.setEndDate(resultSet.getTimestamp("EndDate"));
                order.setDestination(resultSet.getString("Destination"));
                order.setSource(resultSet.getString("Source"));
                order.setPublishDate(resultSet.getTimestamp("PublishDate"));
                order.setWatches(resultSet.getInt("Watches"));

                order.setRequests(OrderRequest.getRequestsByOrderID(order.getOrderID()));
                order.setPerson(User.getUserByID(order.getPersonID()));

                order.getPerson().clear();
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
            _package.setConsumerID(resultSet.getInt("ConsumerID"));
            _package.setProducerID(resultSet.getInt("ProducerID"));
            _package.setGetterID(resultSet.getInt("GetterID"));
            _package.setPackageID(resultSet.getInt("PackageID"));
            _package.setStatus(resultSet.getInt("Status"));

            _package.setSourceLatitude(resultSet.getFloat("SourceLatitude"));
            _package.setDestinationLatitude(resultSet.getFloat("DestinationLatitude"));
            _package.setSourceLongitude(resultSet.getFloat("SourceLongitude"));
            _package.setDestinationLongitude(resultSet.getFloat("DestinationLongitude"));
            _package.setDestinationAddress(resultSet.getString("DestinationAddress"));
            _package.setSourceAddress(resultSet.getString("SourceAddress"));

            byte[] deliveryPhotoProof = resultSet.getBytes("DeliveryProofPhoto");
            if (deliveryPhotoProof != null) {
                _package.setDeliveryProofPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(deliveryPhotoProof));
            }

            byte[] transferPhotoProof = resultSet.getBytes("TransferProofPhoto");
            if (transferPhotoProof != null) {
                _package.setTransferProofPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(transferPhotoProof));

            }

            _package.setText(resultSet.getString("Text"));
            _package.setEventDate(resultSet.getTimestamp("EventDate"));
            _package.setFinishDate(resultSet.getTimestamp("FinishDate"));

            _package.setProducer(User.getUserByID(_package.getProducerID()));
            _package.setConsumer(User.getUserByID(_package.getConsumerID()));

            if (_package.getStatus() != -1) {
                _package.setGetter(User.getUserByID(_package.getGetterID()));
                _package.getGetter().clear();
            }

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
                message.setDate(resultSet.getTimestamp("Date"));
                message.setDialogID(resultSet.getInt("DialogID"));
                message.setMessageID(resultSet.getInt("MessageID"));
                message.setWatched(resultSet.getInt("Watched"));
            }
        }

        return message;
    }

    public static Dialog getDialogByQuery(String query, Boolean type, int personID) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Dialog dialog = new Dialog();

        if (resultSet != null) {
            while (resultSet.next()) {
                dialog.setDialogID(resultSet.getInt("DialogID"));
                dialog.setPersonID_1(resultSet.getInt("PersonID_1"));
                dialog.setPersonID_2(resultSet.getInt("PersonID_2"));

                if (type) {
                    dialog.setPerson(User.getUserByID(personID));
                }

                dialog.setMessages(Message.getMessagesByDialogID(dialog.getDialogID()));
            }
        }

        return dialog;
    }

    public static ArrayList<Dialog> getDialogsByQuery(String query, int personID) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<Dialog> dialogs = new ArrayList<>();

        if (resultSet != null) {
            while (resultSet.next()) {
                Dialog dialog = new Dialog();
                dialog.setDialogID(resultSet.getInt("DialogID"));
                dialog.setPersonID_1(resultSet.getInt("PersonID_1"));
                dialog.setPersonID_2(resultSet.getInt("PersonID_2"));

                if (dialog.getPersonID_1() != personID) {
                    dialog.setPerson(User.getUserByID(dialog.getPersonID_1()));
                } else {
                    dialog.setPerson(User.getUserByID(dialog.getPersonID_2()));
                }

                dialog.setMessages(Message.getMessagesByDialogID(dialog.getDialogID()));
                dialogs.add(dialog);
            }
        }

        return dialogs;
    }

    public static ArrayList<Message> getMessagesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<Message> messages = new ArrayList<>();

        while (resultSet.next()) {
            Message message = new Message();
            message.setPersonID(resultSet.getInt("PersonID"));
            message.setText(resultSet.getString("Text"));
            message.setDate(resultSet.getTimestamp("Date"));
            message.setDialogID(resultSet.getInt("DialogID"));
            message.setMessageID(resultSet.getInt("MessageID"));
            message.setWatched(resultSet.getInt("Watched"));
            messages.add(message);
        }

        return messages;
    }

    public static PackageOrder getOrderByQuery(String query) throws Exception {

        ResultSet resultSet = getSelectResultSet(query);
        PackageOrder order = new PackageOrder();

        if (resultSet != null) {
            while (resultSet.next()) {
                order.setOrderID(resultSet.getInt("OrderID"));
                order.setText(resultSet.getString("Text"));
                order.setPersonID(resultSet.getInt("PersonID"));
                order.setStartDate(resultSet.getTimestamp("StartDate"));
                order.setEndDate(resultSet.getTimestamp("EndDate"));
                order.setDestination(resultSet.getString("Destination"));
                order.setSource(resultSet.getString("Source"));
                order.setPublishDate(resultSet.getTimestamp("PublishDate"));
                order.setWatches(resultSet.getInt("Watches"));

                order.setRequests(OrderRequest.getRequestsByOrderID(order.getOrderID()));
                order.setPerson(User.getUserByID(order.getPersonID()));

                order.getPerson().clear();
            }
        }
        return order;
    }

    public static PackageOffer getOfferByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        PackageOffer offer = new PackageOffer();

        if (resultSet != null) {
            while (resultSet.next()) {
                offer.setOfferID(resultSet.getInt("OfferID"));
                offer.setText(resultSet.getString("Text"));
                offer.setPersonID(resultSet.getInt("PersonID"));
                offer.setStartDate(resultSet.getTimestamp("StartDate"));
                offer.setEndDate(resultSet.getTimestamp("EndDate"));
                offer.setDestination(resultSet.getString("Destination"));
                offer.setSource(resultSet.getString("Source"));
                offer.setPublishDate(resultSet.getTimestamp("PublishDate"));
                offer.setWatches(resultSet.getInt("Watches"));

                offer.setRequests(OfferRequest.getRequestsByOfferID(offer.getOfferID()));
                offer.setPerson(User.getUserByID(offer.getPersonID()));
            }
        }

        return offer;
    }

    public static Package getPackageByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Package _package = new Package();

        while (resultSet.next()) {
            _package.setConsumerID(resultSet.getInt("ConsumerID"));
            _package.setProducerID(resultSet.getInt("ProducerID"));
            _package.setGetterID(resultSet.getInt("GetterID"));
            _package.setPackageID(resultSet.getInt("PackageID"));
            _package.setStatus(resultSet.getInt("Status"));

            _package.setSourceLatitude(resultSet.getFloat("SourceLatitude"));
            _package.setDestinationLatitude(resultSet.getFloat("DestinationLatitude"));
            _package.setSourceLongitude(resultSet.getFloat("SourceLongitude"));
            _package.setDestinationLongitude(resultSet.getFloat("DestinationLongitude"));
            _package.setDestinationAddress(resultSet.getString("DestinationAddress"));
            _package.setSourceAddress(resultSet.getString("SourceAddress"));

            _package.setText(resultSet.getString("Text"));
            _package.setEventDate(resultSet.getTimestamp("EventDate"));
            _package.setFinishDate(resultSet.getTimestamp("FinishDate"));

            byte[] deliveryPhotoProof = resultSet.getBytes("DeliveryProofPhoto");
            if (deliveryPhotoProof != null) {
                _package.setDeliveryProofPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(deliveryPhotoProof));
            }

            byte[] transferPhotoProof = resultSet.getBytes("TransferProofPhoto");
            if (transferPhotoProof != null) {
                _package.setTransferProofPhoto(javax.xml.bind.DatatypeConverter.printBase64Binary(transferPhotoProof));
            }

            _package.setProducer(User.getUserByID(_package.getProducerID()));
            _package.setConsumer(User.getUserByID(_package.getConsumerID()));
            _package.setGetter(User.getUserByID(_package.getGetterID()));

            _package.getGetter().clear();
            _package.getConsumer().clear();
            _package.getProducer().clear();
        }
        return _package;
    }

    public static ArrayList<OfferRequest> getOfferRequestsByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<OfferRequest> requests = new ArrayList<>();

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
        ArrayList<OrderRequest> requests = new ArrayList<>();

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
                return request;
            }
        }

        throw new Exception("Такого запроса не существует");
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
            return request;
        }

        throw new Exception("Такого запроса не существует");
    }

    public static ArrayList<Event> getEventsByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<Event> events = new ArrayList<Event>();

        if (resultSet != null) {
            while (resultSet.next()) {
                Event event = new Event();
                event.setDate(resultSet.getTimestamp("Date"));
                event.setEventID(resultSet.getInt("EventID"));
                event.setPersonID(resultSet.getInt("PersonID"));
                event.setText(resultSet.getString("Text"));
                events.add(event);
            }
        }
        return events;
    }

    public static String getPhotoByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);

        if (resultSet != null) {
            while (resultSet.next()) {
                byte[] ph = resultSet.getBytes("Photo");
                if (ph != null) {
                    return javax.xml.bind.DatatypeConverter.printBase64Binary(ph);
                }
            }
        }
        return "";
    }

    public static int getIntByQuery(String query, String paramName) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        if (resultSet != null) {
            while (resultSet.next()) {
                try {
                    int result = resultSet.getInt(0);
                    return result;
                } catch (Exception ex) {
                    int result = resultSet.getInt(paramName);
                    return result;
                }
            }
        }
        return 0;
    }

    public static Disputes getDisputesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Disputes disputes = new Disputes();
        disputes.setDisputes(new ArrayList<Dispute>());

        if (resultSet != null) {
            while (resultSet.next()) {
                Dispute dispute = new Dispute();
                dispute.setStatus(resultSet.getInt("Status"));
                dispute.setDisputeID(resultSet.getInt("DisputeID"));
                dispute.setPublishDate(resultSet.getTimestamp("PublishDate"));
                dispute.setPackageID(resultSet.getInt("PackageID"));
                dispute.setPersonID(resultSet.getInt("PersonID"));
                dispute.setText(resultSet.getString("Text"));
                dispute.set_package(Package.getPackageByID(dispute.getPackageID()));
                dispute.setPerson(User.getUserByID(dispute.getPersonID()));
                disputes.getDisputes().add(dispute);
            }
        }

        return disputes;
    }

    private static ResultSet getSelectResultSet(String query) throws Exception {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("coursew.database.windows.net");
        ds.setPortNumber(1433);
        ds.setDatabaseName("CourseWID");
        ds.setEncrypt(true);
        ds.setPassword("hsepassword16)");
        ds.setUser("HSEADMIN");
        ds.setHostNameInCertificate("*.database.windows.net");
        ds.setTrustServerCertificate(false);
        ds.setLoginTimeout(100);

        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet;

        resultSet = statement.executeQuery(query);

        return resultSet;
    }

    public static void loadPhoto(String command, byte[] array) throws Exception {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("coursew.database.windows.net");
        ds.setPortNumber(1433);
        ds.setDatabaseName("CourseWID");
        ds.setEncrypt(true);
        ds.setPassword("hsepassword16)");
        ds.setUser("HSEADMIN");
        ds.setHostNameInCertificate("*.database.windows.net");
        ds.setTrustServerCertificate(false);
        ds.setLoginTimeout(1000);

        Connection connection = ds.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command);
        preparedStatement.setBytes(1, array);

        preparedStatement.executeUpdate();
    }

    public static Date get3DayBeforeDate(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -3);
        return calendar.getTime();
    }
}
