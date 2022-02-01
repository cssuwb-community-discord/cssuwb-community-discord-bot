package com.cssuwbcommunity.csuwbbot.Modules.Reddit;

public enum RedditCategory {
    HOT("hot"),
    BEST("best"),
    NEW("new"),
    RISING("rising"),
    CONTROVERSIAL("controversial");
    private final String name;

    RedditCategory(final String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
