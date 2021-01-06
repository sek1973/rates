package sk.servlets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/** This servlet provides data about rates EUR - USD */
@Path("/")
public class RatesServlet {
    /** Get newest data */
    @GET
    public Response getRates() {
        // get rate data from server
        RateData data = RestHelper.getLatestRates();
        // log received data to database
        DbHelper.logRequest(data);
        // return response in HTML format
        return Response.ok(data.rate.toString()).build();
    }

    /** Get data on the last working day relating to provided date in format YYYY-MM-DD */
    @GET
    @Path("{date}")
    public Response getRateOnDay(@PathParam("date") String dateString) {
        // get rate data from server
        RateData data = RestHelper.getRatesForDay(dateString);
        // log received data to database
        DbHelper.logRequest(data);
        // return response in HTML format
        return Response.ok(data.rate.toString()).build();
    }
}
