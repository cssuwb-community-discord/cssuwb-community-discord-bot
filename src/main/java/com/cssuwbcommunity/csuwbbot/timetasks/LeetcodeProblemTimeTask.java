package com.cssuwbcommunity.csuwbbot.timetasks;

import com.cssuwbcommunity.csuwbbot.discord.DiscordBotService;
import com.cssuwbcommunity.csuwbbot.discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeProblem;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeProblemFilter;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeService;
import com.cssuwbcommunity.csuwbbot.registration.SettingsService;
import java.util.TimerTask;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("leetcodeProblemTimeTask")
public class LeetcodeProblemTimeTask extends TimerTask {
    private static final Logger logger = LoggerFactory
        .getLogger(AskRedditTimeTask.class);
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
            logger.info("Beginning execution of LeetcodeProblemTimeTask");
            logger.debug("Creating problem filter");
            final LeetcodeProblemFilter filter =
                new LeetcodeProblemFilter(false);
            logger.debug("Fetching problem");
            final LeetcodeProblem leetcodeProblem = leetcodeService
                .fetchRandomProblem(filter);
            logger.debug("Creating message embed for problem");
            final MessageEmbed embed = embedCreationService
                .getEmbed(leetcodeProblem);
            logger.debug("Fetching channel id from settings");
            final String channelID = settingsService
                .getSettingsObject()
                .get("discord")
                .getAsJsonObject()
                .get("leetcodeChannel")
                .getAsString();
            final MessageChannel channel = discordBotService
                .getDiscordInterface()
                .getTextChannelById(channelID);
            logger.debug("Sending embed");
            channel.sendMessageEmbeds(embed)
                .queue(message -> DiscordBotService.createThreadOnMessage(message, leetcodeProblem.getTitle()));
            logger.info("Finished execution of LeetcodeProblemTimeTask");
        }
        catch(Exception e) {
            logger.error("An error occured in executing LeetcodeProblemTimeTask", e);
        }
    }
}
