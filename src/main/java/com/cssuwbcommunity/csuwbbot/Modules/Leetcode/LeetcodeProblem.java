package com.cssuwbcommunity.csuwbbot.Modules.Leetcode;

import java.util.List;
import lombok.Getter;

@Getter
public class LeetcodeProblem {
    private String title;
    private String titleSlug;
    private String difficulty;
    private int likes;
    private List<String> topicTags;
    private int totalSubmission;
    private int totalAccepted;
    public String getProblemURL() {
        return "https://leetcode.com/problems/" + titleSlug;
    }
    public double getAcceptancePercentage() {
        return ((double) totalAccepted/ totalSubmission)*100.0;
    }
}
