package com.cssuwbcommunity.csuwbbot.discord.slash;

import com.cssuwbcommunity.csuwbbot.discord.EmbedCreationService;
import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.HackerrankProblem;
import com.cssuwbcommunity.csuwbbot.modules.Hackerrank.HackerrankService;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import org.springframework.stereotype.Component;

@Component("hackerrankSlash")
public class HackerrankSlash implements SlashFunctionality{
    private final HackerrankService hackerrankService;
    private final EmbedCreationService embedCreationService;
    @Autowired
    public HackerrankSlash(final HackerrankService hackerrankService
        , final EmbedCreationService embedCreationService) {
        this.hackerrankService = hackerrankService;
        this.embedCreationService = embedCreationService;
    }
    @Override
    public ReplyCallbackAction execute(final SlashCommandInteractionEvent event)
        throws IOException {
        final HackerrankProblem hackerrankProblem = hackerrankService
                .fetchHackerrankProblem();
        final MessageEmbed problemEmbed = embedCreationService
            .getEmbed(hackerrankProblem);
        return event.replyEmbeds(problemEmbed);
    }
}
