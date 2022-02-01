package com.cssuwbcommunity.csuwbbot.Modules.Leetcode;

import com.cssuwbcommunity.csuwbbot.CsuwbbotApplication;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.stereotype.Service;

@Service("leetcodeProblemFetchingService")
public class LeetcodeProblemFetchingService {
    private static final String GRAPHQL_LINK =
        "https://leetcode.com/graphql";
    private final JsonObject queryObject;
    public LeetcodeProblemFetchingService() {
        this.queryObject = loadQueryJson();
    }
    public String fetchRandomProblem(final LeetcodeProblemFilter filter)
        throws IOException {
        final String randomQuery = queryObject
            .get("randomQuery")
            .getAsString();
        final JsonObject filterJson = filter.getJsonObject();
        final JsonObject getBodyJson = new JsonObject();
        getBodyJson.add("variables", filterJson);
        getBodyJson.addProperty("query", randomQuery);
        final String getBodyString = getBodyJson.toString();
        final URL graphQlURI = new URL(GRAPHQL_LINK);
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
        String result = "";
        if (connection.getResponseCode() == 200) {
            final BufferedInputStream inputStream = new BufferedInputStream(
                connection.getInputStream());
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            buffer.write(inputStream.readAllBytes());
            result = buffer.toString(StandardCharsets.UTF_8);
        }
        return result;
    }
    private JsonObject loadQueryJson() {
        final InputStream settingsStream =
            CsuwbbotApplication.class.getClassLoader()
                .getResourceAsStream("graphqlqueries.json");
        final BufferedReader settingsReader =
            new BufferedReader(new InputStreamReader(settingsStream,
                StandardCharsets.UTF_8));
        final JsonObject queryObject = JsonParser
            .parseReader(settingsReader)
            .getAsJsonObject();
        return queryObject;
    }
}
