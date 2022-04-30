package com.cssuwbcommunity.csuwbbot.modules.Reddit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Random;

import static com.cssuwbcommunity.csuwbbot.constants.RedditConstants.REDDIT_FORMAT;

@AllArgsConstructor
public class FetchAskRedditProblemTask {
    private final Gson gson;
    public RedditPost execute()
        throws IOException {
        final String link = MessageFormat.format(REDDIT_FORMAT, "askreddit","hot","all");
        System.out.println(link);
        final URL redditURL = new URL(link);
        final HttpsURLConnection connection = (HttpsURLConnection)
            redditURL.openConnection();
        connection.setRequestMethod("GET");
        //Workaround to get around Too Many Request codes
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        final InputStream inputStream = connection.getInputStream();
        final int statusCode = connection.getResponseCode();
        if(statusCode == 200) {
            final Reader reader = new InputStreamReader(inputStream);
            final JsonObject rawPostJson = JsonParser
                .parseReader(reader)
                .getAsJsonObject();
            final JsonArray rawPostsArray = rawPostJson
                .get("data")
                .getAsJsonObject()
                .get("children")
                .getAsJsonArray();
            final Random random = new Random();
            final JsonObject rawPostObject = rawPostsArray
                .get(random.nextInt(rawPostsArray.size()))
                .getAsJsonObject()
                .get("data")
                .getAsJsonObject();
            final RedditPost redditPost = gson
                .fromJson(rawPostObject, RedditPost.class);
            return redditPost;
        }
        throw new IOException("Could not open URL connection. Status code " + statusCode);
    }
}
