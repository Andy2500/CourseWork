package ru.hse.coursework.controllers;

import ru.hse.coursework.models.Dispute.Dispute;
import ru.hse.coursework.models.Dispute.Disputes;
import ru.hse.coursework.models.Service.DefaultClass;
import ru.hse.coursework.models.Service.Service;
import ru.hse.coursework.models.User.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/dispute")
public class DisputesController {

    @POST
    @Path("/crd/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass createDispute(@FormParam("token") String token,
                                      @FormParam("personID") int id,
                                      @FormParam("date") String date,
                                      @FormParam("packageID") int packageID,
                                      @FormParam("type") int type,
                                      @FormParam("text") String text) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                new Dispute(packageID, type, id, text);
                return new DefaultClass(true, "");
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }


    @POST
    @Path("/cld/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closeDispute(@FormParam("token") String token,
                                     @FormParam("personID") int id,
                                     @FormParam("date") String date,
                                     @FormParam("disputeID") int disputeID) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Dispute.changeStatus(disputeID, 1);
                return new DefaultClass(true, "");
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/gud/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Disputes getUserDisputes(@FormParam("token") String token,
                                    @FormParam("personID") int id,
                                    @FormParam("date") String date) {
        try {
            User user = User.getUserByID(id);
            if (Service.makeToken(user.getToken() + date).equals(token)) {
                Disputes disputes = Disputes.getDisputesByPersonID(id);
                disputes.setDefaultClass(new DefaultClass(true, ""));
                return disputes;
            }
            throw new Exception("token error");
        } catch (Exception ex) {
            Disputes disputes = new Disputes();
            disputes.setDefaultClass(new DefaultClass(false, ex.getLocalizedMessage()));
            return disputes;
        }
    }
}
