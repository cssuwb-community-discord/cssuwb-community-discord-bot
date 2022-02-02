package com.cssuwbcommunity.csuwbbot.Modules.Leetcode;

import java.util.List;
import lombok.Getter;

@Getter
public class LeetcodeProblemDetails {
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
    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("Title: ");
        buffer.append(title);
        buffer.append("\nTitle Slug: ");
        buffer.append(titleSlug);
        buffer.append("\nDifficulty: ");
        buffer.append(difficulty);
        buffer.append("\nLikes: ");
        buffer.append(likes);
        buffer.append("\nTotal Accepted Submissions: ");
        buffer.append(totalAccepted);
        buffer.append("\nTotal Submissions: ");
        buffer.append(totalSubmission);
        buffer.append("\nProblem tags: ");
        topicTags.stream().forEach(tag -> {
            buffer.append(tag);
            buffer.append(", ");
        });
        buffer.delete(buffer.length() - 2, buffer.length());
        return buffer.toString();
    }
}
