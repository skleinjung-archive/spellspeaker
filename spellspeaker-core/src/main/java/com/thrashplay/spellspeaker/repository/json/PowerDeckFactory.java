package com.thrashplay.spellspeaker.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thrashplay.spellspeaker.SpellspeakerException;
import com.thrashplay.spellspeaker.config.PowerDeckEntryConfiguration;
import com.thrashplay.spellspeaker.model.PowerDeck;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
@Service
public class PowerDeckFactory {
    private static final String JSON_RESOURCE_PATH = "/com/thrashplay/spellspeaker/config/power_deck.json";

    private static final Gson gson = new Gson();

    private List<PowerDeckEntryConfiguration> configurations;
    private String configurationJson;

    public String getConfigurationJson() {
        return configurationJson;
    }

    public void setConfigurationJson(String configurationJson) {
        this.configurationJson = configurationJson;
        // clear configurations using old json
        configurations = null;
    }

    public PowerDeck createPowerDeck() {
        ensureConfigurationsLoaded();

        PowerDeck result = new PowerDeck();
        for (PowerDeckEntryConfiguration configuration : configurations) {
            for (int i = 0; i < configuration.getQuantity(); i++) {
                result.addPowerCard(configuration.getValue());
            }
        }
        result.reshuffle();

        return result;
    }

    private void ensureConfigurationsLoaded() {
        if (configurations == null) {
            if (configurationJson == null) {
                configurationJson = readJson();
            }

            Reader reader = null;
            try {
                reader = new StringReader(configurationJson);

                Type listType = new TypeToken<List<PowerDeckEntryConfiguration>>() {}.getType();
                configurations = gson.fromJson(reader, listType);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
    }

    private String readJson() {
        InputStream inputStream = null;
        try {
            inputStream = JsonCardConfigurationRepository.class.getResourceAsStream(JSON_RESOURCE_PATH);
            return IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            throw new SpellspeakerException("Failed to read power deck configuration JSON: " + e.toString(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
