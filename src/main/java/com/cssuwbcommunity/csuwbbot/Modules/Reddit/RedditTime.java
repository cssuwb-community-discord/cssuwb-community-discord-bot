package com.cssuwbcommunity.csuwbbot.Modules.Reddit;

public enum RedditTime {
    HOUR("hour"),
    DAILY("day"),
    WEEK("week"),
    MONTH("new"),
    YEAR("year"),
    ALL("all");
    private final String name;

    RedditTime(final String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
