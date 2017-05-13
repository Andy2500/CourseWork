package ru.hse.coursework.controllers;

import ru.hse.coursework.models.DefaultClass;
import ru.hse.coursework.models.Dispute.Dispute;
import ru.hse.coursework.models.Dispute.Disputes;
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
                                      @FormParam("packageID") int packageID,
                                      @FormParam("type") int type,
                                      @FormParam("text") String text) {
        try {
            User user = User.getUserByToken(token);
            new Dispute(packageID, type, user.getPersonID(), text);
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }


    @POST
    @Path("/cld/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public DefaultClass closeDispute(@FormParam("token") String token,
                                     @FormParam("disputeID") int disputeID) {
        try {
            User.getUserByToken(token);
            Dispute.changeStatus(disputeID, 1);
            return new DefaultClass(true, "");

        } catch (Exception ex) {
            return new DefaultClass(false, ex.getLocalizedMessage());
        }
    }

    @POST
    @Path("/gud/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Disputes getUserDisputes(@FormParam("token") String token) {
        try {
            User user = User.getUserByToken(token);
            Disputes disputes = Disputes.getDisputesByPersonID(user.getPersonID());
            disputes.setDefaultClass(new DefaultClass(true, ""));
            return disputes;

        } catch (Exception ex) {
            Disputes disputes = new Disputes();
            disputes.setDefaultClass(new DefaultClass(false, ex.getLocalizedMessage()));
            return disputes;
        }
    }

    @POST
    @Path("/gad/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Disputes getAllDisputes(@FormParam("token") String token) {
        try {
            User.getUserByToken(token);
            Disputes disputes = Disputes.getAllDisputes();
            disputes.setDefaultClass(new DefaultClass(true, ""));
            return disputes;
        } catch (Exception ex) {
            Disputes disputes = new Disputes();
            disputes.setDefaultClass(new DefaultClass(false, ex.getLocalizedMessage()));
            return disputes;
        }
    }
}
