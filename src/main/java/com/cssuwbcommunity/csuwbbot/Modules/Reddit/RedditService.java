package com.cssuwbcommunity.csuwbbot.Modules.Reddit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service("redditService")
public class RedditService {
    private static final String redditFormat =
        "https://www.reddit.com/r/%s/%s.json?t=%s";
    private final Gson gson;
    public RedditService() {
        this.gson = new Gson();
    }
    public RedditPost getRedditPost(final String subreddit,
        final RedditCategory category, final RedditTime time)
        throws InterruptedException, IOException, URISyntaxException{
        final List<JsonObject> rawPostsObjects = fetchPosts(subreddit,
            category,time);
        final JsonObject postObject = pickRandomPost(rawPostsObjects);
        final RedditPost redditPost = gson.fromJson(postObject, RedditPost.class);
        return redditPost;
    }
    private List<JsonObject> fetchPosts(final String subreddit,
        final RedditCategory category, final RedditTime time)
        throws InterruptedException, IOException, URISyntaxException {
        final String link = String.format(redditFormat, subreddit,
            category.getName(), time.getName());
        final URI uri = new URI(link);
        final HttpClient httpClient = HttpClient
            .newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .GET()
            .build();
        final HttpResponse<String> response = httpClient
            .send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
        final JsonObject rawPostObject = JsonParser
            .parseString(response.body())
            .getAsJsonObject();
        final JsonArray rawPostsArray = rawPostObject
            .get("data")
            .getAsJsonObject()
            .get("children")
            .getAsJsonArray();
        final Type listType = new TypeToken<List<JsonObject>>() {}.getType();
        final List<JsonObject> objectList =
            new Gson().fromJson(rawPostsArray, listType);
        return objectList;
    }
    private JsonObject pickRandomPost(final List<JsonObject> postObjects) {
        final Random random = new Random();
        final JsonObject rawPostObject = postObjects
            .get(random.nextInt(postObjects.size()))
            .get("data")
            .getAsJsonObject();
        final JsonObject postObject = new JsonObject();
        postObject.addProperty("author", rawPostObject
            .get("author")
            .getAsString());
        postObject.addProperty("title", rawPostObject
            .get("title")
            .getAsString());
        postObject.addProperty("url", rawPostObject
            .get("url")
            .getAsString());
        postObject.addProperty("subreddit", rawPostObject
            .get("subreddit")
            .getAsString());
        return postObject;
    }
}
