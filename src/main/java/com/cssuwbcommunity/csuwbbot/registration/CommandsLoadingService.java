package com.cssuwbcommunity.csuwbbot.registration;

import com.cssuwbcommunity.csuwbbot.CsuwbbotApplication;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service("commandsLoadingService")
public class CommandsLoadingService {
    @Getter
    private List<JsonObject> commandsObject;
    public CommandsLoadingService() {
        commandsObject = loadCommandObjects();
    }
    private List<JsonObject> loadCommandObjects() {
        final InputStream commandStream =
            CsuwbbotApplication.class.getClassLoader()
                .getResourceAsStream("commands.json");
        final BufferedReader commandReader =
            new BufferedReader(new InputStreamReader(commandStream,
                StandardCharsets.UTF_8));
        final JsonArray commandArray = JsonParser.parseReader(commandReader)
            .getAsJsonObject()
            .get("commands")
            .getAsJsonArray();
        final Type arrayToken = new TypeToken<List<JsonObject>>(){}.getType();
        return new Gson().fromJson(commandArray, arrayToken);
    }
}
