package com.cssuwbcommunity.csuwbbot.modules.Hackerrank;

import lombok.Getter;

import java.util.List;

@Getter
public class HackerrankProblem {
    private String slug;
    private String name;
    private String created_at;
    private String updated_at;
    private long total_count;
    private long solved_count;
    private double success_ratio;
    private List<String> tag_names;
    private String difficulty_name;
    private String skill;
    private String skill_slug;
    private HackerrankTrack track;
}
