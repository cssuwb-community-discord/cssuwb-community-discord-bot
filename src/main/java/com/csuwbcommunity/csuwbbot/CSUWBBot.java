package com.csuwbcommunity.csuwbbot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class CSUWBBot {
    public static void main(String[] args) {
        try {
            registerCommands();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void registerCommands()
        throws InterruptedException, IOException, URISyntaxException{
        final InputStream commandStream =
                CSUWBBot.class.getClassLoader()
                    .getResourceAsStream("commands.json");
        final BufferedReader commandReader =
                new BufferedReader(new InputStreamReader(commandStream,
                    StandardCharsets.UTF_8));
        final InputStream settingsStream =
                CSUWBBot.class.getClassLoader()
                    .getResourceAsStream("settings.json");
        final BufferedReader settingsReader =
                new BufferedReader(new InputStreamReader(settingsStream,
                    StandardCharsets.UTF_8));
        final JsonObject settingsObject = JsonParser
                .parseReader(settingsReader)
                .getAsJsonObject();
        final JsonArray commandArray = JsonParser.parseReader(commandReader)
                .getAsJsonObject()
                .get("commands")
                .getAsJsonArray();
        final HttpClient httpClient = HttpClient.newHttpClient();
        final String authString = "Bot " + settingsObject
                .get("token")
                .getAsString();
        final String applicationID = settingsObject
            .get("application_id")
            .getAsString();
        final String guildID = settingsObject
            .get("guild_id")
            .getAsString();
        final String uriString = String
            .format("https://discord.com/api/v8/applications/%s/guilds/%s/commands",
                applicationID, guildID);
        final URI uri = new URI(uriString);
        final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .header("Authorization", authString)
                .headers("Content-Type", "application/json")
                .uri(uri);
        for(JsonElement element: commandArray) {
            final JsonObject commandObject = (JsonObject) element;
            final String commandString = commandObject.toString();
            final HttpRequest request = requestBuilder
                    .POST(BodyPublishers.ofString(commandString))
                    .build();
            final HttpResponse<String> response =
                httpClient.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
            System.out.println(response.body());
        }
    }
}
