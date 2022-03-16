package com.cssuwbcommunity.csuwbbot.timetasks;

import com.cssuwbcommunity.csuwbbot.discord.DiscordBotService;
import com.cssuwbcommunity.csuwbbot.discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.HackerrankProblem;
import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.HackerrankService;
import com.cssuwbcommunity.csuwbbot.registration.SettingsService;
import java.util.TimerTask;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("hackerrankProblemTimeTask")
public class HackerrankProblemTimeTask extends TimerTask {
    private static final Logger logger = LoggerFactory
        .getLogger(AskRedditTimeTask.class);
    private final HackerrankService hackerrankService;
    private final DiscordBotService discordBotService;
    private final EmbedCreationService embedCreationService;
    private final SettingsService settingsService;

    public HackerrankProblemTimeTask(final HackerrankService hackerrankService,
        final DiscordBotService discordBotService,
        final EmbedCreationService embedCreationService,
        final SettingsService settingsService) {
        this.hackerrankService = hackerrankService;
        this.discordBotService = discordBotService;
        this.embedCreationService = embedCreationService;
        this.settingsService = settingsService;
    }
    @Override
    public void run() {
        try {
            logger.info("Beginning execution of HackerrankProblemTimeTask");
            logger.debug("Fetching problem");
            final HackerrankProblem hackerrankProblem = hackerrankService
                .fetchHackerrankProblem();
            logger.debug("Creating message embed for problem");
            final MessageEmbed embed = embedCreationService
                .getEmbed(hackerrankProblem);
            logger.debug("Fetching channel id from settings");
            final String channelID = settingsService
                .getSettingsObject()
                .get("discord")
                .getAsJsonObject()
                .get("hackerrankChannel")
                .getAsString();
            final MessageChannel channel = discordBotService
                .getDiscordInterface()
                .getTextChannelById(channelID);
            logger.debug("Sending embed");
            channel.sendMessageEmbeds(embed)
                .queue(message -> DiscordBotService.createThreadOnMessage(message, hackerrankProblem.getName()));
            logger.info("Finished execution of HackerrankProblemTimeTask");
        }
        catch(Exception e) {
            logger.error("An error occured in executing HackerrankProblemTimeTask", e);
        }
    }
}
