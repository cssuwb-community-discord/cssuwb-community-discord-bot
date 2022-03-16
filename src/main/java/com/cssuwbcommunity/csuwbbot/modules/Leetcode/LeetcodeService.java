package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("leetcodeService")
public class LeetcodeService {
    private final LeetcodeProblemParsingService leetcodeProblemParsingService;
    private final LeetcodeProblemFetchingService leetcodeProblemFetchingService;
    private final Gson gson;
    @Autowired
    public LeetcodeService(final LeetcodeProblemFetchingService leetcodeProblemFetchingService,
                           final LeetcodeProblemParsingService leetcodeProblemParsingService) {
        this.leetcodeProblemFetchingService = leetcodeProblemFetchingService;
        this.leetcodeProblemParsingService = leetcodeProblemParsingService;
        this.gson = new Gson();
    }
    public LeetcodeProblem fetchRandomProblem(final LeetcodeProblemFilter filter)
        throws IOException {
        final JsonObject responseBody = leetcodeProblemFetchingService
            .fetchRandomProblem(filter);
        final LeetcodeProblem problemDetails = processResponse(responseBody);
        return problemDetails;
    }
    private LeetcodeProblem processResponse(final JsonObject rawJson) {
        final JsonObject questionJson = rawJson
            .get("data")
            .getAsJsonObject()
            .get("randomQuestion")
            .getAsJsonObject();
        final JsonObject taggedJson = leetcodeProblemParsingService
            .rewriteTags(questionJson);
        final JsonObject statsParsed = leetcodeProblemParsingService
            .parseStats(taggedJson);
        final LeetcodeProblem problemDetails = gson
            .fromJson(statsParsed, LeetcodeProblem.class);
        return problemDetails;
    }
}
