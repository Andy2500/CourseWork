package ru.hse.coursework.service;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import ru.hse.coursework.models.Chat.Dialog;
import ru.hse.coursework.models.Chat.Message;
import ru.hse.coursework.models.Dispute.Dispute;
import ru.hse.coursework.models.Dispute.Disputes;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBManager {

    public static Connection getConnection() throws SQLServerException {
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

        return ds.getConnection();
    }

    public static void execCommand(String command) throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute(command);
    }

    public static ResultSet getSelectResultSet(String query) throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void loadPhoto(String command, byte[] array) throws Exception {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(command);
        preparedStatement.setBytes(1, array);

        preparedStatement.executeUpdate();
    }


    public static User getUserByQuery(String query) throws Exception {
        User user = new User();
        ResultSet resultSet = getSelectResultSet(query);

        if (resultSet.next()) {
            user = User.parseUserFromResultSet(resultSet);
        }

        return user;
    }

    public static Users getUsersByQuery(String query) throws Exception {
        Users users = new Users();
        users.setUsers(new ArrayList<User>());

        ResultSet resultSet = getSelectResultSet(query);

        while (resultSet.next()) {
            User user = User.parseUserFromResultSet(resultSet);
            users.getUsers().add(user);
        }

        return users;
    }


    public static ArrayList<Response> getResponsesByQuery(String query) throws Exception {
        ArrayList<Response> responses = new ArrayList<>();
        ResultSet resultSet = getSelectResultSet(query);

        while (resultSet.next()) {
            responses.add(Response.parseResponseFromResultSet(resultSet));
        }

        return responses;
    }

    public static Response getResponseByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Response response = new Response();

        if (resultSet.next()) {
            response = Response.parseResponseFromResultSet(resultSet);
        }

        return response;
    }

    public static ArrayList<Comment> getCommentsByQuery(String query) throws Exception {
        ArrayList<Comment> comments = new ArrayList<>();
        ResultSet resultSet = getSelectResultSet(query);

        while (resultSet.next()) {
            comments.add(Comment.parseCommentFromResultSet(resultSet));
        }

        return comments;
    }

    public static Offers getOffersByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Offers offers = new Offers();
        ArrayList<PackageOffer> offerArrayList = new ArrayList<>();

        while (resultSet.next()) {
            offerArrayList.add(PackageOffer.parseOfferFromResultSet(resultSet));
        }

        offers.setOffers(offerArrayList);
        return offers;
    }

    public static Orders getOrdersByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Orders orders = new Orders();
        ArrayList<PackageOrder> orderArrayList = new ArrayList<>();

        while (resultSet.next()) {
            orderArrayList.add(PackageOrder.parseOrderFromResultSet(resultSet));
        }

        orders.setOrders(orderArrayList);
        return orders;
    }

    public static Packages getPackagesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Packages packages = new Packages();
        ArrayList<Package> packageArrayList = new ArrayList<>();

        while (resultSet.next()) {
            packageArrayList.add(Package.parsePackageFromResultSet(resultSet));
        }

        packages.setPackages(packageArrayList);
        return packages;
    }

    public static Dialog getDialogByQuery(String query, Boolean type, int personID) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Dialog dialog = new Dialog();

        while (resultSet.next()) {
            dialog = Dialog.parseDialogFromResultSet(resultSet);
            if (type) {
                dialog.setPerson(User.getUserByID(personID));
            }

        }

        return dialog;
    }

    public static ArrayList<Dialog> getDialogsByQuery(String query, int personID) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<Dialog> dialogs = new ArrayList<>();


        while (resultSet.next()) {
            Dialog dialog = Dialog.parseDialogFromResultSet(resultSet);

            if (dialog.getPersonID_1() != personID) {
                dialog.setPerson(User.getUserByID(dialog.getPersonID_1()));
            } else {
                dialog.setPerson(User.getUserByID(dialog.getPersonID_2()));
            }

            dialogs.add(dialog);
        }


        return dialogs;
    }

    public static ArrayList<Message> getMessagesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<Message> messages = new ArrayList<>();

        while (resultSet.next()) {
            messages.add(Message.parseMessageFromResultSet(resultSet));
        }

        return messages;
    }

    public static PackageOrder getOrderByQuery(String query) throws Exception {

        ResultSet resultSet = getSelectResultSet(query);
        PackageOrder order = new PackageOrder();

        if (resultSet != null) {
            while (resultSet.next()) {
                order = PackageOrder.parseOrderFromResultSet(resultSet);
            }
        }
        return order;
    }

    public static PackageOffer getOfferByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        PackageOffer offer = new PackageOffer();

        if (resultSet != null) {
            while (resultSet.next()) {
                offer = PackageOffer.parseOfferFromResultSet(resultSet);
            }
        }

        return offer;
    }

    public static Package getPackageByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Package _package = new Package();

        while (resultSet.next()) {
            _package = Package.parsePackageFromResultSet(resultSet);
        }
        return _package;
    }

    public static ArrayList<OfferRequest> getOfferRequestsByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<OfferRequest> requests = new ArrayList<>();


        while (resultSet.next()) {
            requests.add(OfferRequest.parseOfferRequestFromResultSet(resultSet));
        }


        return requests;
    }

    public static ArrayList<OrderRequest> getOrderRequestsByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        ArrayList<OrderRequest> requests = new ArrayList<>();

        while (resultSet.next()) {
            requests.add(OrderRequest.parseOrderRequestFromResultSet(resultSet));
        }

        return requests;
    }

    public static OfferRequest getOfferRequestByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        OfferRequest request = new OfferRequest();

        if (resultSet.next()) {
            request = OfferRequest.parseOfferRequestFromResultSet(resultSet);
            return request;
        }

        throw new Exception("Такого запроса не существует");
    }

    public static OrderRequest getOrderRequestByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        OrderRequest request = new OrderRequest();

        if (resultSet.next()) {
            request = OrderRequest.parseOrderRequestFromResultSet(resultSet);
            return request;
        }

        throw new Exception("Такого запроса не существует");
    }

    public static int getIntByQuery(String query, String paramName) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        if (resultSet != null) {
            if (resultSet.next()) {
                try {
                    return resultSet.getInt(0);
                } catch (Exception ex) {
                    return resultSet.getInt(paramName);
                }
            }
        }
        return 0;
    }

    public static Disputes getDisputesByQuery(String query) throws Exception {
        ResultSet resultSet = getSelectResultSet(query);
        Disputes disputes = new Disputes();
        disputes.setDisputes(new ArrayList<Dispute>());


        while (resultSet.next()) {
            disputes.getDisputes().add(Dispute.parseDisputeFromResultSet(resultSet));
        }

        return disputes;
    }
}
