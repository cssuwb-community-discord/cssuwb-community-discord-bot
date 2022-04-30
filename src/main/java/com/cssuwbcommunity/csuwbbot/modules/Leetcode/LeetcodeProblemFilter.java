package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeetcodeProblemFilter {
    private final String difficulty;
    private final boolean premiumOnly=false;
}
