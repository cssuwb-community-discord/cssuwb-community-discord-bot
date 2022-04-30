package com.cssuwbcommunity.csuwbbot.modules.Reddit;

import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RedditPost {
    private String author;
    private String title;
    private String url;
    private String subreddit;
    public JsonObject getEmbedJson() {
        final JsonObject embedJson = new JsonObject();
        embedJson.addProperty("title", title);
        embedJson.addProperty("url", url);
        embedJson.addProperty("color", 16711680);
        embedJson.add("author", getAuthorJson());
        embedJson.add("footer", getFooter());
        return embedJson;
    }
    private JsonObject getFooter() {
        final JsonObject footerObject = new JsonObject();
        final String footerText = "From " + subreddit;
        footerObject.addProperty("text", footerText);
        return footerObject;
    }
    private JsonObject getAuthorJson() {
        final JsonObject authorObject = new JsonObject();
        authorObject.addProperty("name", author);
        authorObject.addProperty("url", "https://reddit.com/u/"+author);
        return authorObject;
    }
}
