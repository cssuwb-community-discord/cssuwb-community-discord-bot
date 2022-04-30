package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import static com.cssuwbcommunity.csuwbbot.constants.LeetcodeConstants.GET_RANDOM_PROBLEM_TEMPLATE;
import static com.cssuwbcommunity.csuwbbot.constants.LeetcodeConstants.RANDOM_GRAPHQL_QUERY;
import static com.cssuwbcommunity.csuwbbot.constants.LeetcodeConstants.GRAPHQL_LINK;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@AllArgsConstructor
public class FetchLeetcodeProblemTask {

    private final Gson gson;
    private final LeetcodeQueryVariables variables;
    public LeetcodeProblem execute()
        throws IOException {
        final String filterJsonString = gson.toJson(variables);
        final String queryString = MessageFormat
                .format(GET_RANDOM_PROBLEM_TEMPLATE, RANDOM_GRAPHQL_QUERY, filterJsonString);
        System.out.println(queryString);
        final URL leetcodeURL = new URL(GRAPHQL_LINK);
        final HttpsURLConnection connection = (HttpsURLConnection) leetcodeURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        final OutputStream outputStream = connection.getOutputStream();
        outputStream.write(queryString.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
        final int statusCode = connection.getResponseCode();
        if(statusCode == 200) {
            final Reader inputStream = new InputStreamReader(
                connection.getInputStream(), StandardCharsets.UTF_8);
            final JsonObject rawObject = JsonParser.parseReader(inputStream).getAsJsonObject();
            final JsonObject questionObject = rawObject
                .getAsJsonObject("data")
                .getAsJsonObject("randomQuestion");
            final LeetcodeProblem problemObject = gson
                .fromJson(questionObject, LeetcodeProblem.class);
            return problemObject;
        }
        throw new IOException("Could not retrieve problem. Status code " + statusCode);
    }
}
