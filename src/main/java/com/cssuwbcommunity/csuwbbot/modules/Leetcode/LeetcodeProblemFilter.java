package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import com.google.gson.JsonObject;
import java.util.HashSet;
import lombok.Getter;

@Getter
public class LeetcodeProblemFilter {
    private final static HashSet<String> DIFFICULTIES = new HashSet<>(){{
        add("");
        add("EASY");
        add("MEDIUM");
        add("HARD");
    }};
    private final String difficulty;
    private final boolean premiumOnly;
    private final String categorySlug;
    public LeetcodeProblemFilter(final boolean premiumOnly){
        this("", premiumOnly, "");
    }
    public LeetcodeProblemFilter(final String difficulty, final boolean premiumOnly){
        this(difficulty, premiumOnly, "");
    }
    public LeetcodeProblemFilter(final String difficulty, final boolean premiumOnly,
        final String categorySlug) {
        if(!DIFFICULTIES.contains(difficulty))
            throw new IllegalArgumentException("Invalid difficulty provided");
        this.categorySlug = categorySlug;
        this.premiumOnly = premiumOnly;
        this.difficulty = difficulty;
    }
    public JsonObject getJsonObject() {
        final JsonObject returnObject = new JsonObject();
        returnObject.addProperty("categorySlug", categorySlug);
        final JsonObject filterObject = new JsonObject();
        if(!difficulty.isEmpty())
            filterObject.addProperty("difficulty", difficulty);
        filterObject.addProperty("premiumOnly", premiumOnly);
        returnObject.add("filters", filterObject);
        return returnObject;
    }
}
