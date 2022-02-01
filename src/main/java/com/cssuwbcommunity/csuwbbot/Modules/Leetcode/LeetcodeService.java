package com.cssuwbcommunity.csuwbbot.Modules.Leetcode;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("leetcodeService")
public class LeetcodeService {
    private LeetcodeProblemParsingService leetcodeProblemParsingService;
    private LeetcodeProblemFetchingService leetcodeProblemFetchingService;
    private final Gson gson;
    @Autowired
    public LeetcodeService(final LeetcodeProblemFetchingService leetcodeProblemFetchingService,
                           final LeetcodeProblemParsingService leetcodeProblemParsingService) {
        this.leetcodeProblemFetchingService = leetcodeProblemFetchingService;
        this.leetcodeProblemParsingService = leetcodeProblemParsingService;
        this.gson = new Gson();
    }
    public LeetcodeProblemDetails fetchRandomProblem(final LeetcodeProblemFilter filter)
        throws IOException {
        final String responseBody = leetcodeProblemFetchingService
            .fetchRandomProblem(filter);
        final LeetcodeProblemDetails problemDetails = processResponse(responseBody);
        return problemDetails;
    }
    private LeetcodeProblemDetails processResponse(final String response) {
        final JsonObject rawJson = JsonParser
            .parseString(response)
            .getAsJsonObject();
        final JsonObject questionJson = rawJson
            .get("data")
            .getAsJsonObject()
            .get("randomQuestion")
            .getAsJsonObject();
        final JsonObject taggedJson = leetcodeProblemParsingService
            .rewriteTags(questionJson);
        final JsonObject statsParsed = leetcodeProblemParsingService
            .parseStats(taggedJson);
        final LeetcodeProblemDetails problemDetails = gson
            .fromJson(statsParsed, LeetcodeProblemDetails.class);
        return problemDetails;
    }
}
