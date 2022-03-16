package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import com.cssuwbcommunity.csuwbbot.CsuwbbotApplication;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.stereotype.Service;

@Service("leetcodeProblemFetchingService")
public class LeetcodeProblemFetchingService {
    private final JsonObject queryObject;
    public LeetcodeProblemFetchingService() {
        this.queryObject = loadQueryJson();
    }
    public JsonObject fetchRandomProblem(final LeetcodeProblemFilter filter)
        throws IOException {
        final String randomQuery = queryObject
            .get("graphQLQueries")
            .getAsJsonObject()
            .get("random")
            .getAsString();
        final JsonObject filterJson = filter.getJsonObject();
        final JsonObject getBodyJson = new JsonObject();
        getBodyJson.add("variables", filterJson);
        getBodyJson.addProperty("query", randomQuery);
        final String getBodyString = getBodyJson.toString();
        final String graphQLLink = queryObject
            .get("graphQLLink")
            .getAsString();
        final URL graphQlURI = new URL(graphQLLink);
        final HttpsURLConnection connection = (HttpsURLConnection)
            graphQlURI.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        final OutputStream outputStream = connection.getOutputStream();
        outputStream.write(getBodyString.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
        if (connection.getResponseCode() == 200) {
            final Reader inputStream = new InputStreamReader(
                    connection.getInputStream(), StandardCharsets.UTF_8);
            final JsonObject resultObject = JsonParser
                    .parseReader(inputStream)
                    .getAsJsonObject();
            return resultObject;
        }
        throw new IOException("Could not fetch problem");
    }
    private JsonObject loadQueryJson() {
        final InputStream settingsStream =
            CsuwbbotApplication.class.getClassLoader()
                .getResourceAsStream("leetcode.json");
        final BufferedReader settingsReader =
            new BufferedReader(new InputStreamReader(settingsStream,
                StandardCharsets.UTF_8));
        final JsonObject queryObject = JsonParser
            .parseReader(settingsReader)
            .getAsJsonObject();
        return queryObject;
    }
}
