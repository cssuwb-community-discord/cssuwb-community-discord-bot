package com.cssuwbcommunity.csuwbbot.modules.Hackerrank;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("hackerrankService")
public class HackerrankService {
    private final HackerrankProblemFetchingService hackerrankProblemFetchingService;
    private final Gson gson;
    @Autowired
    public HackerrankService(final HackerrankProblemFetchingService hackerrankProblemFetchingService) {
        this.hackerrankProblemFetchingService = hackerrankProblemFetchingService;
        this.gson = new Gson();
    }
    public HackerrankProblem fetchHackerrankProblem()
        throws IOException {
        final JsonObject rawProblemObject = hackerrankProblemFetchingService
                .fetchRandomProblem();
        final HackerrankProblem parsedProblem = parseResponse(rawProblemObject);
        return parsedProblem;
    }
    private HackerrankProblem parseResponse(final JsonObject rawJson) {
        return this.gson.fromJson(rawJson, HackerrankProblem.class);
    }
}
