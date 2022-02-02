package com.cssuwbcommunity.csuwbbot.TimeTasks;

import com.cssuwbcommunity.csuwbbot.Discord.DiscordBotService;
import com.cssuwbcommunity.csuwbbot.Discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditCategory;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditPost;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditService;
import com.cssuwbcommunity.csuwbbot.Modules.Reddit.RedditTime;
import com.cssuwbcommunity.csuwbbot.registration.SettingsService;
import java.util.Set;
import java.util.TimerTask;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("askRedditTimeTask")
public class AskRedditTimeTask extends TimerTask {
    private final Logger logger = LoggerFactory.getLogger(AskRedditTimeTask.class);
    private final RedditService redditService;
    private final DiscordBotService discordBotService;
    private final EmbedCreationService embedCreationService;
    private final SettingsService settingsService;
    @Autowired
    public AskRedditTimeTask(final DiscordBotService discordBotService,
        final RedditService redditService,
        final SettingsService settingsService,
        final EmbedCreationService embedCreationService) {
        this.redditService = redditService;
        this.discordBotService = discordBotService;
        this.embedCreationService = embedCreationService;
        this.settingsService = settingsService;
    }
    @Override
    public void run() {
        try {
            final RedditPost post = redditService
                .getRedditPost("askreddit", RedditCategory.HOT, RedditTime.ALL);
            final MessageEmbed embed = embedCreationService.buildRedditEmbed(post);
            final String channelID = settingsService
                .getSettingsObject()
                .get("discord")
                .getAsJsonObject()
                .get("redditChannel")
                .getAsString();
            final MessageChannel channel = discordBotService
                .getDiscordInterface()
                .getTextChannelById(channelID);
            channel.sendMessageEmbeds(embed);
        }
        catch(Exception e) {
            logger.error("An error occured in executing AskRedditTimeTask", e);
        }
    }
}
