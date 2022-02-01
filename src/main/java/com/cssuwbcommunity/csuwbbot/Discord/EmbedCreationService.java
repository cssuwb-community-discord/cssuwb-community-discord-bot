package com.cssuwbcommunity.csuwbbot.Discord;

import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeProblemDetails;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditPost;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Service;

@Service("embedCreationService")
public class EmbedCreationService {
    public MessageEmbed buildRedditEmbed(final RedditPost redditPost) {
        final EmbedBuilder builder = new EmbedBuilder()
            .setTitle(redditPost.getTitle(), redditPost.getUrl())
            .setAuthor(redditPost.getAuthor())
            .setColor(new Color(255, 0, 0))
            .setFooter("From " + redditPost.getSubreddit());
        return builder.build();
    }
    public MessageEmbed getLeetcodeProblemEmbed(final LeetcodeProblemDetails problemDetails) {
        final StringBuffer acceptBuffer = new StringBuffer();
        acceptBuffer.append(problemDetails.getTotalAccepted());
        acceptBuffer.append("/");
        acceptBuffer.append(problemDetails.getTotalSubmission());
        acceptBuffer.append(" (");
        acceptBuffer.append(String.format("%.2f", problemDetails.getAcceptancePercentage()));
        acceptBuffer.append("%)");
        final EmbedBuilder builder = new EmbedBuilder()
            .setTitle(problemDetails.getTitle(), problemDetails.getProblemURL())
            .setColor(new Color(235, 150, 30))
            .addField("Difficulty", problemDetails.getDifficulty(), false)
            .addField("Acceptance Rate", acceptBuffer.toString(), false);
        return builder.build();
    }
}
