package com.cssuwbcommunity.csuwbbot.modules.Reddit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

import static com.cssuwbcommunity.csuwbbot.constants.CredentialConstants.BOT_TOKEN;
import static com.cssuwbcommunity.csuwbbot.constants.CredentialConstants.LEETCODE_CHANNEL_ID;
import static com.cssuwbcommunity.csuwbbot.constants.CredentialConstants.REDDIT_CHANNEL_ID;
import static com.cssuwbcommunity.csuwbbot.constants.DiscordConstants.AUTHORIZATION_HEADER_TEMPLATE;
import static com.cssuwbcommunity.csuwbbot.constants.DiscordConstants.EMBED_BODY_FORMAT;
import static com.cssuwbcommunity.csuwbbot.constants.DiscordConstants.POST_MESSAGE_ENDPOINT_TEMPLATE;

@AllArgsConstructor
public class PostAskRedditPostTask {
    private RedditPost redditPost;
    public void execute() {
        final JsonObject postJson = redditPost.getEmbedJson();
        final JsonArray embedArray = new JsonArray();
        embedArray.add(postJson);
        final String requestBody = MessageFormat.format(EMBED_BODY_FORMAT, embedArray);
        try {
            final URL apiURL = new URL(MessageFormat.format(POST_MESSAGE_ENDPOINT_TEMPLATE, REDDIT_CHANNEL_ID));
            final HttpsURLConnection connection = (HttpsURLConnection) apiURL.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", MessageFormat.format(AUTHORIZATION_HEADER_TEMPLATE, BOT_TOKEN));
            final OutputStream connectionOutput = connection.getOutputStream();
            connectionOutput.write(requestBody.getBytes(StandardCharsets.UTF_8));
            connectionOutput.close();
            final int statusCode = connection.getResponseCode();
            if(statusCode == 200) {
                System.out.println("Message sent successfully");
                return;
            }
            throw new IOException("Could not post message. Status code " + statusCode);
        }
        catch(MalformedURLException e) {
            System.err.println("Could not reach API URL");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
