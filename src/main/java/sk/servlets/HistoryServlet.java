package sk.servlets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

@Path("/history")
public class HistoryServlet {
    /** List all rates request sent */
    @GET
    public Response getHistory() {
        try {
            ArrayList<HistoryEntity> data = DbHelper.getHistory();
            return Response.ok(this.generateHistoryTable(data)).build();
        } catch(Exception e) {
            return Response.ok(e.toString()).build();
        }
    }

    /** Format history collection as HTML table */
    private String generateHistoryTable(ArrayList<HistoryEntity> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<thead><th>ID</th><th>Time stamp</th><th>Date</th><th>Rate</th></thead>");
        sb.append("<tbody>");
        for (HistoryEntity entry : data) {
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(entry.id);
            sb.append("</td>");
            sb.append("<td>");
            sb.append(entry.ts);
            sb.append("</td>");
            sb.append("<td>");
            sb.append(entry.date);
            sb.append("</td>");
            sb.append("<td>");
            sb.append(entry.rate);
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</tbody>");
        sb.append("</table>");
        return sb.toString();
    }

}
