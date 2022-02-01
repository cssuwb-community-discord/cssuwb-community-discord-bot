package com.cssuwbcommunity.csuwbbot.Discord.Slash;

import java.io.IOException;
import java.net.URISyntaxException;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;

public interface SlashFunctionality {
    ReplyAction execute(final SlashCommandEvent event) throws IOException, URISyntaxException, InterruptedException;
}
