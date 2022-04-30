package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeetcodeProblem {
    private short questionId;
    private String title;
    private String titleSlug;
    private String difficulty;
    private String categoryTitle;
    private boolean isPaidOnly;
    private int likes;
    private int dislikes;
    private List<TopicTag> topicTags;
    private final LeetcodeStats stats;
    public String getProblemURL() {
        return "https://leetcode.com/problems/" + titleSlug;
    }
    public JsonObject getEmbedJSON() {
        final JsonObject embedJson = new JsonObject();
        final JsonArray fieldsArray = new JsonArray();
        fieldsArray.add(getDifficultyField());
        fieldsArray.add(getCategoryField());
        fieldsArray.add(getAcceptanceRateField());
        fieldsArray.add(getLikesField());
        fieldsArray.add(getDislikesField());
        embedJson.addProperty("title", title);
        embedJson.addProperty("url", getProblemURL());
        embedJson.addProperty("color", 15439390);
        embedJson.add("author", getAuthorObject());
        embedJson.add("fields", fieldsArray);
        return embedJson;
    }
    public static JsonObject getAuthorObject() {
        final JsonObject authorObject = new JsonObject();
        authorObject.addProperty("name", "Leetcode");
        authorObject.addProperty("url", "https://leetcode.com");
        authorObject.addProperty("icon_url", "https://pbs.twimg.com/profile_images/910592237695676416/7xInX10u_200x200.jpg");
        return authorObject;
    }
    private JsonObject getDifficultyField() {
        final JsonObject fieldObject = new JsonObject();
        fieldObject.addProperty("name", "Difficulty");
        fieldObject.addProperty("value", difficulty);
        fieldObject.addProperty("inline", false);
        return fieldObject;
    }
    private JsonObject getCategoryField() {
        final JsonObject fieldObject = new JsonObject();
        fieldObject.addProperty("name", "Category");
        fieldObject.addProperty("value", categoryTitle);
        fieldObject.addProperty("inline", false);
        return fieldObject;
    }
    private JsonObject getAcceptanceRateField() {
        final JsonObject fieldObject = new JsonObject();
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stats.getTotalAcceptedRaw());
        stringBuilder.append("/");
        stringBuilder.append(stats.getTotalSubmissionRaw());
        stringBuilder.append(" (");
        stringBuilder.append(stats.getAcRate());
        stringBuilder.append(")");
        fieldObject.addProperty("name", "Acceptance Rate");
        fieldObject.addProperty("value", stringBuilder.toString());
        fieldObject.addProperty("inline", false);
        return fieldObject;
    }
    private JsonObject getLikesField() {
        final JsonObject fieldObject = new JsonObject();
        fieldObject.addProperty("name", "Likes");
        fieldObject.addProperty("value", likes);
        fieldObject.addProperty("inline", true);
        return fieldObject;
    }
    private JsonObject getDislikesField() {
        final JsonObject fieldObject = new JsonObject();
        fieldObject.addProperty("name", "Dislikes");
        fieldObject.addProperty("value", dislikes);
        fieldObject.addProperty("inline", true);
        return fieldObject;
    }
}