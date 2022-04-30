package com.cssuwbcommunity.csuwbbot.modules.Leetcode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeetcodeQueryVariables {
    private final LeetcodeProblemFilter filters = LeetcodeProblemFilter.builder().build();
    @Builder.Default
    private final String categorySlug="";
}
