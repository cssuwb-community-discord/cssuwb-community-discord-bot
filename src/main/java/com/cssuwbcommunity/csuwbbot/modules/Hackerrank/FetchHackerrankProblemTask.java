package com.cssuwbcommunity.csuwbbot.modules.Hackerrank;

import static com.cssuwbcommunity.csuwbbot.constants.HackerrankConstants.OFFSET_MAX;
import static com.cssuwbcommunity.csuwbbot.constants.HackerrankConstants.BASE_LINK;
import static com.cssuwbcommunity.csuwbbot.constants.HackerrankConstants.PROBLEM_LIMIT;
import static com.cssuwbcommunity.csuwbbot.constants.HackerrankConstants.TRACKS;
import static com.cssuwbcommunity.csuwbbot.constants.HackerrankConstants.PARAMETER_BASE;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Random;
@AllArgsConstructor
public class FetchHackerrankProblemTask {
    private Gson gson;
    public HackerrankProblem execute()
        throws IOException {
        final String urlString = buildURLString();
        final URL hackerrankURL = new URL(urlString);
        final HttpsURLConnection connection = (HttpsURLConnection)
            hackerrankURL.openConnection();
        connection.setRequestMethod("GET");
        //Workaround to get post
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        if (connection.getResponseCode() == 200) {
            final Reader inputStream = new InputStreamReader(
                connection.getInputStream(), StandardCharsets.UTF_8);
            final JsonObject resultObject = JsonParser
                .parseReader(inputStream)
                .getAsJsonObject();
            final JsonArray problemObjects = resultObject
                .get("models")
                .getAsJsonArray();
            if(problemObjects.size() <= 0) {
                throw new IOException("Could not fetch hackerrank problem models array");
            }
            final JsonObject problemObject = problemObjects
                .get(0)
                .getAsJsonObject();
            final HackerrankProblem hackerrankProblem = gson
                .fromJson(problemObject.toString(), HackerrankProblem.class);
            return hackerrankProblem;
        }
        throw new IOException("Could not fetch problem");
    }
    private String buildURLString() {
        final StringBuilder stringBuilder = new StringBuilder(BASE_LINK);
        final Random random = new Random();
        final int offset = random.nextInt(OFFSET_MAX);
        final String track = TRACKS[random.nextInt(TRACKS.length)];
        final String parameters = MessageFormat.format(PARAMETER_BASE, PROBLEM_LIMIT, offset);
        stringBuilder.append("/");
        stringBuilder.append(track);
        stringBuilder.append("/");
        stringBuilder.append(parameters);
        return stringBuilder.toString();
    }
}
