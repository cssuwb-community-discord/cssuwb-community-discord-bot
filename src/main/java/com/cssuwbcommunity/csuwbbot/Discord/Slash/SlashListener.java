package com.cssuwbcommunity.csuwbbot.Discord.Slash;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("slashListener")
public class SlashListener extends ListenerAdapter {
    private final Logger logger;
    private final SlashFactory slashFactory;
    @Autowired
    public SlashListener(final SlashFactory slashFactory) {
        this.slashFactory = slashFactory;
        this.logger = LoggerFactory.getLogger(SlashListener.class);
    }
    @Override
    public void onSlashCommand(SlashCommandEvent event){
        final String eventName = event.getName();
        final SlashFunctionality slashFunctionality = slashFactory
            .getSlashFunctionality(eventName);
        try{
            slashFunctionality.execute(event).queue();
        }
        catch (Exception e) {
            event
                .reply("An error has occured in the slash function")
                .setEphemeral(true)
                .queue();
            logger.error("An error occured in processing the slash command", e);
        }
    }
}
