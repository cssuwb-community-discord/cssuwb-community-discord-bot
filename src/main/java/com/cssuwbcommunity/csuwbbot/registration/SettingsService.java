package com.cssuwbcommunity.csuwbbot.registration;

import com.cssuwbcommunity.csuwbbot.CsuwbbotApplication;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component("settingsService")
public class SettingsService {
    @Getter
    private final JsonObject settingsObject;
    public SettingsService() {
        settingsObject = loadSettingsObject();
    }
    private JsonObject loadSettingsObject() {
        final InputStream settingsStream =
            CsuwbbotApplication.class.getClassLoader()
                .getResourceAsStream("settings.json");
        final BufferedReader settingsReader =
            new BufferedReader(new InputStreamReader(settingsStream,
                StandardCharsets.UTF_8));
        final JsonObject settingsObject = JsonParser
            .parseReader(settingsReader)
            .getAsJsonObject();
        return settingsObject;
    }
}
