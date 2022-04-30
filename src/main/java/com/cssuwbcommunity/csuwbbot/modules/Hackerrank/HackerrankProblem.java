package com.cssuwbcommunity.csuwbbot.modules.Hackerrank;

import static com.cssuwbcommunity.csuwbbot.constants.HackerrankConstants.BASE_PROBLEM_URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Getter;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.List;

@Getter
@Builder
public class HackerrankProblem {
    private String slug;
    private String name;
    private String created_at;
    private String updated_at;
    private long total_count;
    private long solved_count;
    private double success_ratio;
    private List<String> tag_names;
    private String difficulty_name;
    private String skill;
    private String skill_slug;
    private HackerrankTrack track;
    public JsonObject getEmbedJSON() {
        final JsonObject embedJson = new JsonObject();
        final JsonArray fieldsArray = new JsonArray();
        fieldsArray.add(getAcceptanceRateField());
        fieldsArray.add(getDifficultyField());
        fieldsArray.add(getTopicTagsField());
        embedJson.addProperty("title", name);
        embedJson.addProperty("url", MessageFormat.format(BASE_PROBLEM_URL, slug));
        embedJson.addProperty("color", 65280);
        embedJson.add("author", getAuthorObject());
        embedJson.add("footer", getFooter());
        embedJson.add("fields", fieldsArray);
        return embedJson;
    }
    public static JsonObject getAuthorObject() {
        final JsonObject authorObject = new JsonObject();
        authorObject.addProperty("name", "Hackerrank");
        authorObject.addProperty("url", "https://hackerrank.com");
        authorObject.addProperty("icon_url", "https://i.imgur.com/3o5I9pS.png");
        return authorObject;
    }
    private JsonObject getFooter() {
        final JsonObject footerObject = new JsonObject();
        final String footerText = "Track: " + getTrack().getTrack_name();
        footerObject.addProperty("text", footerText);
        return footerObject;
    }
    private JsonObject getDifficultyField() {
        final JsonObject fieldObject = new JsonObject();
        fieldObject.addProperty("name", "Difficulty");
        fieldObject.addProperty("value", difficulty_name);
        fieldObject.addProperty("inline", false);
        return fieldObject;
    }
    private JsonObject getAcceptanceRateField() {
        final JsonObject fieldObject = new JsonObject();
        final double acceptanceRateRaw = ((double)solved_count/total_count)*100;
        final NumberFormat numberFormatter = NumberFormat.getNumberInstance();
        numberFormatter.setMaximumFractionDigits(3);
        fieldObject.addProperty("name", "Acceptance Rate");
        fieldObject.addProperty("value", numberFormatter.format(acceptanceRateRaw)+"%");
        fieldObject.addProperty("inline", false);
        return fieldObject;
    }
    private JsonObject getTopicTagsField() {
        final JsonObject fieldObject = new JsonObject();
        final String tagsList = String.join(", ", tag_names);
        fieldObject.addProperty("name", "Topic Tags");
        fieldObject.addProperty("value", tagsList);
        fieldObject.addProperty("inline", false);
        return fieldObject;
    }
}
