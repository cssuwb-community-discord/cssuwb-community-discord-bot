package com.cssuwbcommunity.csuwbbot.Discord.Slash;

import com.cssuwbcommunity.csuwbbot.Discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditCategory;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditPost;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditService;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditTime;
import java.io.IOException;
import java.net.URISyntaxException;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
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
    public ReplyAction execute(final SlashCommandEvent event)
        throws IOException, URISyntaxException, InterruptedException {
        final RedditPost post = redditService
            .getRedditPost("askreddit", RedditCategory.HOT, RedditTime.ALL);
        final MessageEmbed embed = embedCreationService.buildRedditEmbed(post);
        return event.replyEmbeds(embed);
    }
}
