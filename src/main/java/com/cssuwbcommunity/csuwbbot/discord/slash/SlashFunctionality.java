package com.cssuwbcommunity.csuwbbot.discord.slash;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

public interface SlashFunctionality {
    ReplyCallbackAction execute(final SlashCommandInteractionEvent event) throws Exception;
}
