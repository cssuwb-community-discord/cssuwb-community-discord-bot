package com.cssuwbcommunity.csuwbbot.Modules.Hackerrank;

import com.cssuwbcommunity.csuwbbot.CsuwbbotApplication;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

@Service("hackerrankProblemFetchingService")
public class HackerrankProblemFetchingService {
    private static final Logger logger = LoggerFactory
        .getLogger(HackerrankProblemFetchingService.class);
    private final JsonObject queryObject;
    public HackerrankProblemFetchingService() {
        queryObject = loadQueryJson();
    }
    public JsonObject fetchRandomProblem()
        throws IOException {
        final String urlString = buildURLString();
        final URL hackerrankURL = new URL(urlString);
        final HttpsURLConnection connection = (HttpsURLConnection)
                hackerrankURL.openConnection();
        connection.setRequestMethod("GET");
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
            return problemObject;
        }
        throw new IOException("Could not fetch problem");
    }
    private String buildURLString() {
        final Random random = new Random();
        final String baseLink = queryObject
                .get("baseLink")
                .getAsString();
        final String parameterBase = queryObject
                .get("parameterBase")
                .getAsString();
        final JsonArray tracks = queryObject
                .get("tracks")
                .getAsJsonArray();
        final int offsetMax = queryObject
                .get("offsetMax")
                .getAsInt();
        final int limit = queryObject
                .get("limit")
                .getAsInt();
        final int trackIndex = random.nextInt(tracks.size());
        final String track = tracks
                .get(trackIndex)
                .getAsString();
        final int offset = random.nextInt(offsetMax);
        final String parameters = String.format(parameterBase, limit, offset);
        final StringBuffer urlBuffer = new StringBuffer(baseLink);
        urlBuffer.append("/");
        urlBuffer.append(track);
        urlBuffer.append("/");
        urlBuffer.append(parameters);
        return urlBuffer.toString();
    }
    private JsonObject loadQueryJson() {
        final InputStream settingsStream =
                CsuwbbotApplication.class.getClassLoader()
                        .getResourceAsStream("hackerrank.json");
        final BufferedReader settingsReader =
                new BufferedReader(new InputStreamReader(settingsStream,
                        StandardCharsets.UTF_8));
        final JsonObject queryObject = JsonParser
                .parseReader(settingsReader)
                .getAsJsonObject();
        return queryObject;
    }
}
