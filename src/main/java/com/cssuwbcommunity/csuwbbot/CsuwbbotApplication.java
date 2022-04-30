package com.cssuwbcommunity.csuwbbot;

import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.FetchHackerrankProblemTask;
import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.HackerrankProblem;
import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.PostHackerrankProblemTask;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.FetchLeetcodeProblemTask;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeProblem;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeQueryVariables;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeStats;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.PostLeetcodeProblemTask;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.FetchAskRedditProblemTask;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.PostAskRedditPostTask;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.RedditPost;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CsuwbbotApplication {
	public static void main(String[] args) {
		postLeetcodeProblem();
		postHackerrankProblem();
		postRedditQuestion();
	}
	public static void postLeetcodeProblem() {
		try {
			final Gson problemGson = new GsonBuilder()
				.registerTypeAdapter(LeetcodeStats.class, new LeetcodeStats.Deserializer())
				.create();
			final LeetcodeQueryVariables variables = LeetcodeQueryVariables.builder()
				.build();
			final FetchLeetcodeProblemTask fetchLeetcodeProblemTask =
				new FetchLeetcodeProblemTask(problemGson, variables);
			final LeetcodeProblem leetcodeProblem = fetchLeetcodeProblemTask
				.execute();
			final PostLeetcodeProblemTask publishLeetcodeProblemTask =
				new PostLeetcodeProblemTask(leetcodeProblem);
			publishLeetcodeProblemTask.execute();
		}
		catch(Exception e) {
			System.err.println("An error occured in posting the Leetcode problem");
			e.printStackTrace();
		}
	}
	public static void postHackerrankProblem() {
		try {
			final Gson gson = new GsonBuilder()
				.create();
			final FetchHackerrankProblemTask fetchHackerrankProblemTask
				= new FetchHackerrankProblemTask(gson);
			final HackerrankProblem problem = fetchHackerrankProblemTask
				.execute();
			final PostHackerrankProblemTask postHackerrankProblemTask
				= new PostHackerrankProblemTask(problem);
			postHackerrankProblemTask.execute();
		}
		catch(Exception e) {
			System.err.println("An error occured in posting the Hackerrank problem");
			e.printStackTrace();
		}
	}
	public static void postRedditQuestion() {
		try {
			final Gson gson = new GsonBuilder()
				.create();
			final FetchAskRedditProblemTask fetchAskRedditProblemTask
				= new FetchAskRedditProblemTask(gson);
			final RedditPost post = fetchAskRedditProblemTask.execute();
			final PostAskRedditPostTask postAskRedditPostTask =
				new PostAskRedditPostTask(post);
			postAskRedditPostTask.execute();
		}
		catch(Exception e) {
			System.err.println("An error occured in posting the Reddit post");
			e.printStackTrace();
		}
	}
}
