package com.cssuwbcommunity.csuwbbot.discord.slash;

import com.cssuwbcommunity.csuwbbot.discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeService;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeProblem;
import com.cssuwbcommunity.csuwbbot.modules.Leetcode.LeetcodeProblemFilter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("leetcodeSlash")
public class LeetcodeSlash implements SlashFunctionality{
    private static final Logger logger = LoggerFactory
        .getLogger(LeetcodeSlash.class);
    private final LeetcodeService leetcodeService;
    private final EmbedCreationService embedCreationService;
    @Autowired
    public LeetcodeSlash(final LeetcodeService leetcodeService,
        final EmbedCreationService embedCreationService) {
        this.leetcodeService = leetcodeService;
        this.embedCreationService = embedCreationService;
    }
    @Override
    public ReplyCallbackAction execute(final SlashCommandInteractionEvent event) {
        try {
            final boolean paid = false;
            final StringBuffer buffer = new StringBuffer();
            if(event.getOption("difficulty") != null) {
                final String difficultyString = event
                    .getOption("difficulty")
                    .getAsString();
                buffer.append(difficultyString);
            }
            final LeetcodeProblemFilter filter =
                new LeetcodeProblemFilter(buffer.toString(), paid);
            final LeetcodeProblem leetcodeProblem = leetcodeService
                .fetchRandomProblem(filter);
            final MessageEmbed embed = embedCreationService
                .getEmbed(leetcodeProblem);
            return event
                .replyEmbeds(embed);
        }
        catch(Exception e) {
            logger.error("An error occured in fetching a leetcode question", e);
            return event
                .reply("An error occured in fetching a leetcode question")
                .setEphemeral(true);

        }
    }
}
