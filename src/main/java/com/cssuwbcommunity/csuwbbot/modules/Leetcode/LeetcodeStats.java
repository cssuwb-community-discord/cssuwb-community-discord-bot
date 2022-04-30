package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Type;

@Getter
@Builder
public class LeetcodeStats {
    private final String totalAccepted;
    private final String totalSubmission;
    private final long totalAcceptedRaw;
    private final long totalSubmissionRaw;
    private final String acRate;
    public static class Deserializer implements JsonDeserializer<LeetcodeStats> {
        @Override
        public LeetcodeStats deserialize(final JsonElement jsonElement,
                                         final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final String rawJsonString = jsonElement.toString();
            final String jsonString = rawJsonString
                .substring(1, rawJsonString.length()-1)
                .replace("\\", ""); //get rid of leading/trailing double quotes
            final JsonObject statsJson = JsonParser
                .parseString(jsonString)
                .getAsJsonObject();
            return LeetcodeStats.builder()
                .totalAccepted(statsJson.get("totalAccepted").getAsString())
                .totalSubmission(statsJson.get("totalSubmission").getAsString())
                .totalAcceptedRaw(statsJson.get("totalAcceptedRaw").getAsLong())
                .totalSubmissionRaw(statsJson.get("totalSubmissionRaw").getAsLong())
                .acRate(statsJson.get("acRate").getAsString())
                .build();
        }
    }
}
