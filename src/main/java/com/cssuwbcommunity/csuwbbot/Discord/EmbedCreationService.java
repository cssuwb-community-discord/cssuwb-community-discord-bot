package com.cssuwbcommunity.csuwbbot.Discord;

import com.cssuwbcommunity.csuwbbot.Modules.Hackerrank.HackerrankProblem;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeProblem;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditPost;
import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("embedCreationService")
public class EmbedCreationService {
    private static final Logger logger = LoggerFactory
        .getLogger(EmbedCreationService.class);
    private final static String baseProblemURL = "https://www.hackerrank.com/challenges/%s/problem";
    public MessageEmbed getEmbed(final RedditPost redditPost) {
        final EmbedBuilder builder = new EmbedBuilder()
            .setTitle(redditPost.getTitle(), redditPost.getUrl())
            .setAuthor(redditPost.getAuthor())
            .setColor(new Color(255, 0, 0))
            .setFooter("From " + redditPost.getSubreddit());
        return builder.build();
    }
    public MessageEmbed getEmbed(final LeetcodeProblem problemDetails) {
        final StringBuffer acceptBuffer = new StringBuffer();
        final StringBuffer likeBuffer = new StringBuffer();
        acceptBuffer.append(problemDetails.getTotalAccepted());
        acceptBuffer.append("/");
        acceptBuffer.append(problemDetails.getTotalSubmission());
        acceptBuffer.append(" (");
        acceptBuffer.append(String.format("%.2f", problemDetails.getAcceptancePercentage()));
        acceptBuffer.append("%)");
        final EmbedBuilder builder = new EmbedBuilder()
            .setAuthor("Leetcode", "https://leetcode.com", "https://pbs.twimg.com/profile_images/910592237695676416/7xInX10u_200x200.jpg")
            .setTitle(problemDetails.getTitle(), problemDetails.getProblemURL())
            .setColor(new Color(235, 150, 30))
            .addField("Difficulty", problemDetails.getDifficulty(), false)
            .addField("Category", problemDetails.getCategoryTitle(), false)
            .addField("Acceptance Rate", acceptBuffer.toString(), false)
            .addField("Likes", String.valueOf(problemDetails.getLikes()), true)
            .addField("Dislikes", String.valueOf(problemDetails.getDislikes()), true);
        return builder.build();
    }
    public MessageEmbed getEmbed(final HackerrankProblem problemDetails) {
        final String problemURL = String.format(baseProblemURL, problemDetails.getSlug());
        final StringBuffer acceptBuffer = new StringBuffer();
        final StringBuffer tagsBuffer = new StringBuffer();
        acceptBuffer.append(problemDetails.getSolved_count());
        acceptBuffer.append("/");
        acceptBuffer.append(problemDetails.getTotal_count());
        acceptBuffer.append(" (");
        acceptBuffer.append(String.format("%.2f", problemDetails.getSuccess_ratio()*100));
        acceptBuffer.append("%)");
        problemDetails.getTag_names().stream().forEach(tag -> {
            tagsBuffer.append(tag);
            tagsBuffer.append(", ");
        });
        tagsBuffer.delete(tagsBuffer.length()-2, tagsBuffer.length());
        final EmbedBuilder builder = new EmbedBuilder()
            .setAuthor("Hackerrank", "https://hackerrank.com", "https://i.imgur.com/3o5I9pS.png")
            .setTitle(problemDetails.getName(), problemURL)
            .setColor(new Color(0,255,0))
            .addField("Difficulty", problemDetails.getDifficulty_name(), false)
            .addField("Acceptance Rate", acceptBuffer.toString(), false)
            .addField("Topic Tags", tagsBuffer.toString(), false)
            .setFooter("Track: " + problemDetails.getTrack().getTrack_name());
        return builder.build();
    }
}
