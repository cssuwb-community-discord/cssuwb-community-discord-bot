package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import java.util.List;
import lombok.Getter;

@Getter
public class LeetcodeProblem {
    private short questionId;
    private String title;
    private String titleSlug;
    private String difficulty;
    private String categoryTitle;
    private boolean isPaidOnly;
    private int likes;
    private int dislikes;
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
