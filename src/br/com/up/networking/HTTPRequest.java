package br.com.up.networking;

import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class HTTPRequest {

    // Method to request the API, if the API is not responding, null will be returned and the program will exit

    public JSONObject requestGetMethod(String stringUrl) {

        try {

            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            String response = IOUtils.toString(connection.getInputStream(), "UTF-8");
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
            
        }

    }

}
