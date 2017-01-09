package com.training.Medianet.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by root on 06/01/17.
 */
public class JsonParserClass {

    public static String getJson(String url){

        InputStream is = null;
        String result = "";
        JSONObject jsonObject = null;

        // HTTP
        try {
            HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e) {
            return null;
        }

        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch(Exception e) {
            return null;
        }

        return result;

        // Convert string to object
       /* try {
            jsonObject = new JSONObject(result);
        } catch(JSONException e) {
            return null;
        }

        return jsonObject;*/
    }

}
