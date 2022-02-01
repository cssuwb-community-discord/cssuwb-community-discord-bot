package com.cssuwbcommunity.csuwbbot.Discord.Slash;

import com.cssuwbcommunity.csuwbbot.Discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeService;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeProblemDetails;
import com.cssuwbcommunity.csuwbbot.Modules.Leetcode.LeetcodeProblemFilter;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("leetcodeSlash")
public class LeetcodeSlash implements SlashFunctionality{
    private final LeetcodeService leetcodeService;
    private final EmbedCreationService embedCreationService;
    @Autowired
    public LeetcodeSlash(final LeetcodeService leetcodeService,
        final EmbedCreationService embedCreationService) {
        this.leetcodeService = leetcodeService;
        this.embedCreationService = embedCreationService;
    }
    @Override
    public ReplyAction execute(final SlashCommandEvent event) {
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
            final LeetcodeProblemDetails leetcodeProblemDetails = leetcodeService
                .fetchRandomProblem(filter);
            final MessageEmbed embed = embedCreationService
                .getLeetcodeProblemEmbed(leetcodeProblemDetails);
            return event
                .replyEmbeds(embed);
        }
        catch(Exception e) {
            return event
                .reply("An error occured in fetching a leetcode question")
                .setEphemeral(true);
        }
    }
}
