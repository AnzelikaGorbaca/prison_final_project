package com.prison.project.service.fugitive;

import com.prison.project.model.Fugitive;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class FugitiveService {

    @Value("${api.apiUrl}")
    private String apiUrl;

    public JSONObject getConnection() {
        try {
            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.setRequestProperty("Content-Type", "application/json; utf-8"); //header
            con.setRequestProperty("Accept", "application/json");


            int responseCode = con.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
//                System.out.println(response.toString());
                JSONParser parser = new JSONParser();
                JSONObject data = (JSONObject) parser.parse(response.toString());
                return data;
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Fugitive> getFugitiveList() {
        JSONObject data = getConnection();

        JSONArray fugitives = (JSONArray) data.get("items");
        List<Fugitive> result = new ArrayList<>();
        for (Object o : fugitives) {
            JSONObject fugitive = (JSONObject) o;

            JSONArray images = (JSONArray) fugitive.get("images");
            JSONObject image = (JSONObject) images.get(0);
            String fugitiveImage = (String) image.get("large");

            String details = (String) fugitive.get("details");
            details = replace(details);
            String caution = (String) fugitive.get("caution");
            caution = replace(caution);
            String remarks = (String) fugitive.get("remarks");
            remarks = replace(remarks);

            JSONArray infoLinks = (JSONArray) fugitive.get("files");
            JSONObject infoLink = (JSONObject)infoLinks.get(0);
            String pdfLink = (String) infoLink.get("url");


            result.add(new Fugitive(
                    (String) fugitive.get("title"),
                    details,
                    caution,
                    fugitiveImage,
                    (String) fugitive.get("sex"),
                    remarks,
                    (String) fugitive.get("reward_text"),
                    (String) fugitive.get("warning_message"),
                    pdfLink
            ));
        }
        return result;

    }

    private String replace(String fieldText) {
        if (fieldText != null) {
            fieldText = fieldText.replace("<p>", "")
                    .replace("</p>", "")
                    .replace("<br/>",  "")
                    .replace("<br />", "");
        }
        return fieldText;
    }

    public List<Fugitive> getFiveFugitiveList(int indexStart){
        int indexEnd = indexStart+5;
        return getFugitiveList().subList(indexStart, indexEnd);
    }
}
