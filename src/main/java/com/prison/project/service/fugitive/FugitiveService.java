package com.prison.project.service.fugitive;

import com.prison.project.model.Fugitive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Transactional
@AllArgsConstructor
@NoArgsConstructor
@Service
public class FugitiveService {

    @Value("${api.apiUrl}")
    private String apiUrl;

    public JSONObject getConnection() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json; utf-8")
                    .header("Accept", "application/json")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("HttpResponseCode: " + response.statusCode());
            } else {
                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse(response.body());
            }
        } catch (IOException | InterruptedException | ParseException e) {
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
            JSONObject infoLink = (JSONObject) infoLinks.get(0);
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
                    .replace("<br/>", "")
                    .replace("<br />", "");
        }
        return fieldText;
    }

    public List<Fugitive> getFiveFugitiveList(int indexStart) {
        int indexEnd = indexStart + 5;
        return getFugitiveList().subList(indexStart, indexEnd);
    }
}
