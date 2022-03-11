package com.cssuwbcommunity.csuwbbot.Discord.Slash;

import java.io.IOException;
import java.net.URISyntaxException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public interface SlashFunctionality {
    ReplyCallbackAction execute(final SlashCommandInteractionEvent event) throws Exception;
}
