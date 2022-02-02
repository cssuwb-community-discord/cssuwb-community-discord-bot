package com.cssuwbcommunity.csuwbbot.TimeTasks;

import com.cssuwbcommunity.csuwbbot.Discord.DiscordBotService;
import com.cssuwbcommunity.csuwbbot.Discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeProblemDetails;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeProblemFilter;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeService;
import com.cssuwbcommunity.csuwbbot.registration.SettingsService;
import java.util.TimerTask;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("leetcodeProblemTimeTask")
public class LeetcodeProblemTimeTask extends TimerTask {
    private final Logger logger = LoggerFactory.getLogger(AskRedditTimeTask.class);
    private final LeetcodeService leetcodeService;
    private final DiscordBotService discordBotService;
    private final EmbedCreationService embedCreationService;
    private final SettingsService settingsService;

    public LeetcodeProblemTimeTask(final LeetcodeService leetcodeService,
        final DiscordBotService discordBotService,
        final EmbedCreationService embedCreationService,
        final SettingsService settingsService) {
        this.leetcodeService = leetcodeService;
        this.discordBotService = discordBotService;
        this.embedCreationService = embedCreationService;
        this.settingsService = settingsService;
    }
    @Override
    public void run() {
        try {
            final LeetcodeProblemFilter filter =
                new LeetcodeProblemFilter(false);
            final LeetcodeProblemDetails leetcodeProblemDetails = leetcodeService
                .fetchRandomProblem(filter);
            final MessageEmbed embed = embedCreationService
                .getLeetcodeProblemEmbed(leetcodeProblemDetails);
            final String channelID = settingsService
                .getSettingsObject()
                .get("discord")
                .getAsJsonObject()
                .get("leetcodeChannel")
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
