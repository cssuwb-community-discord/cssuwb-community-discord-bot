package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

@Service("leetcodeProblemParsingService")
public class LeetcodeProblemParsingService {
    public JsonObject parseStats(final JsonObject rawJson) {
        final String statsString = rawJson
            .get("stats")
            .getAsString();
        final JsonObject statsObject = JsonParser
            .parseString(statsString)
            .getAsJsonObject();
        rawJson.remove("stats");
        final int totalSubmission = statsObject
            .get("totalSubmissionRaw")
            .getAsInt();
        final int totalAccepted = statsObject
            .get("totalAcceptedRaw")
            .getAsInt();
        rawJson.addProperty("totalSubmission", totalSubmission);
        rawJson.addProperty("totalAccepted", totalAccepted);
        return rawJson;
    }
    public JsonObject rewriteTags(final JsonObject rawJson) {
        final JsonArray tags = rawJson
            .get("topicTags")
            .getAsJsonArray();
        final JsonArray tagsStringList = new JsonArray();
        for(final JsonElement element: tags) {
            final String tag = element
                .getAsJsonObject()
                .get("name")
                .getAsString();
            tagsStringList.add(tag);
        }
        rawJson.remove("topicTags");
        rawJson.add("topicTags", tagsStringList);
        return rawJson;
    }
}
