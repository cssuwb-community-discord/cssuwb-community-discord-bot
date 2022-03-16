package com.cssuwbcommunity.csuwbbot.discord.slash;

import com.cssuwbcommunity.csuwbbot.discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.RedditCategory;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.RedditPost;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.RedditService;
import com.cssuwbcommunity.csuwbbot.modules.Reddit.RedditTime;
import java.io.IOException;
import java.net.URISyntaxException;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("askRedditSlash")
public class AskRedditSlash implements SlashFunctionality{
    private final RedditService redditService;
    private final EmbedCreationService embedCreationService;
    @Autowired
    public AskRedditSlash(final RedditService redditService,
        final EmbedCreationService embedCreationService) {
        this.redditService = redditService;
        this.embedCreationService = embedCreationService;
    }
    @Override
    public ReplyCallbackAction execute(final SlashCommandInteractionEvent event)
        throws IOException, URISyntaxException, InterruptedException {
        final RedditPost post = redditService
            .getRedditPost("askreddit", RedditCategory.HOT, RedditTime.ALL);
        final MessageEmbed embed = embedCreationService.getEmbed(post);
        return event.replyEmbeds(embed);
    }
}
