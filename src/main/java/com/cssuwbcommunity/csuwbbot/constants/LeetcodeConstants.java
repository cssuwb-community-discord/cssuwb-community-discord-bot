package com.cssuwbcommunity.csuwbbot.constants;

public class LeetcodeConstants {
    public static final String GRAPHQL_LINK = "https://leetcode.com/graphql";
    public static final String RANDOM_GRAPHQL_QUERY = "query randomQuestion($categorySlug: String, $filters: QuestionListFilterInput){randomQuestion(categorySlug: $categorySlug, filters: $filters){questionId  title titleSlug difficulty categoryTitle isPaidOnly likes dislikes stats topicTags{name}}}";
    public static final String SPECIFIC_GRAPQL_QUERY = "query questionData($titleSlug: String!){question(titleSlug: $titleSlug){questionId  title titleSlug difficulty categoryTitle isPaidOnly likes dislikes stats topicTags{name}}}";
    public static final String GET_RANDOM_PROBLEM_TEMPLATE = "'{'\"query\":\"{0}\", \"variables\":{1}'}'";
}
