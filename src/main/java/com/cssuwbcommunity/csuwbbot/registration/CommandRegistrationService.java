package com.cssuwbcommunity.csuwbbot.registration;

import com.google.gson.JsonObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class CommandRegistrationService {
    final Logger logger = LoggerFactory.getLogger(CommandRegistrationService.class);
    @Autowired
    @Qualifier("settingsService")
    SettingsService settingsService;
    @Autowired
    @Qualifier("commandsLoadingService")
    CommandsLoadingService commandsService;
    @Bean
    private void registration() {
        try {
            logger.info("Beginning command registration process");
            logger.info("Registering commands to guild");
            uploadCommands();
            logger.info("Commands successfully uploaded");
        }
        catch(Exception e) {
            logger.error("An error occurred in registering commands");
            logger.error(e.getMessage());
        }
    }
    private void uploadCommands()
        throws URISyntaxException{
        final List<JsonObject> commandsArray = commandsService
            .getCommandsObject();
        final HttpRequest.Builder requestBuilder = getRequestBuilder();
        final HttpClient httpClient = HttpClient.newHttpClient();
        commandsArray.stream()
            .forEach(commandObject -> {
            final String commandString = commandObject.toString();
            final HttpRequest request = requestBuilder
                .POST(HttpRequest.BodyPublishers.ofString(commandString))
                .build();
            try {
                final HttpResponse<String> response =
                    httpClient.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
                logger.debug("Command uploaded. Response: ");
                logger.debug(response.body());
            }
            catch(Exception e) {
                logger.error("Could not upload command object");
                logger.error("Object: " + commandString);
                logger.error(e.getMessage());
            }
        });
    }
    private HttpRequest.Builder getRequestBuilder()
        throws URISyntaxException {
        final JsonObject rawSettingsObject = settingsService.getSettingsObject();
        final JsonObject discordSettingsObject = rawSettingsObject
            .get("discord")
            .getAsJsonObject();
        final String authString = "Bot " + discordSettingsObject
            .get("bot_token")
            .getAsString();
        final String applicationID = discordSettingsObject
                .get("application_id")
                .getAsString();
        final String guildID = discordSettingsObject
                .get("guild_id")
                .getAsString();
        final String uriString = String
            .format("https://discord.com/api/v9/applications/%s/guilds/%s/commands",
            applicationID, guildID);
        final URI uri = new URI(uriString);
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
            .header("Authorization", authString)
            .headers("Content-Type", "application/json")
            .uri(uri);
        return requestBuilder;
    }
}