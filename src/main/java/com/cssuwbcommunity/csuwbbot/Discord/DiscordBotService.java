package com.cssuwbcommunity.csuwbbot.Discord;

import com.cssuwbcommunity.csuwbbot.Discord.Slash.SlashListener;
import com.cssuwbcommunity.csuwbbot.registration.SettingsService;
import java.util.Collections;
import javax.security.auth.login.LoginException;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("discordBotService")
public class DiscordBotService {
    @Getter
    private JDA discordInterface;
    private final SettingsService settingsService;
    private final SlashListener slashListener;
    @Autowired
    public DiscordBotService(final SettingsService settingsService, final SlashListener slashListener) {
        this.settingsService = settingsService;
        this.slashListener = slashListener;
    }
    @Bean
    public void initializeBot()
        throws LoginException{
        final String token = settingsService
            .getSettingsObject()
            .get("discord")
            .getAsJsonObject()
            .get("bot_token")
            .getAsString();
        discordInterface = JDABuilder.create(Collections.emptyList())
            .setToken(token)
            .addEventListeners(slashListener)
            .build();
    }

}
