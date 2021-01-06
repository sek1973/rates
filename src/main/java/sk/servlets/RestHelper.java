package sk.servlets;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.*;
/** Class dedicated to handle REST GET request sent to Frankfurter server */
public class RestHelper {
    public static String urlString = "http://api.frankfurter.app";

    /** get rate data in JSON format */
    private static String getResponseData(String urlString) {
        HttpURLConnection connection = null;
        Map<String, String> params = new HashMap<String, String>() {{
            put("to", "USD");
        }};

        try {
            URL url = new URL(urlString + ParameterStringBuilder.getParamsString(params));
            connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP
                    || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM) {
                String location = connection.getHeaderField("Location");
                url = new URL(location);
                connection.disconnect();
                connection = (HttpURLConnection) url.openConnection();
            }

            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();

            System.out.println("Request responseCode: " + responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("Unexpected Http response: " + responseCode);
            }

            Scanner sc = new Scanner(url.openStream());
            String response = "";
            while (sc.hasNext()) {
                String line = sc.nextLine();
                System.out.println(line);
                response += line;
            }
            sc.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /** create RateData object from JSON data */
    private static RateData createRateData(String jsonString) {
        JSONObject obj = new JSONObject(jsonString);
        String date = obj.getString("date");
        Double rate = obj.getJSONObject("rates").getDouble("USD");
        return new RateData(date, rate);
    }

    public static RateData getLatestRates() {
        // create url to get data
        String urlString = RestHelper.urlString + "/latest";
        // get data in JSON format
        String jsonString = RestHelper.getResponseData(urlString);
        // build RateData object from JSON data and return
        return RestHelper.createRateData(jsonString);
    }

    public static RateData getRatesForDay(String dateString) {
        // create url to get data
        String urlString = RestHelper.urlString + "/" + dateString;
        // get data in JSON format
        String jsonString = RestHelper.getResponseData(urlString);
        // build RateData object from JSON data and return
        return RestHelper.createRateData(jsonString);
    }
}
