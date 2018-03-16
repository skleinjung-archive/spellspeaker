package com.thrashplay.spellspeaker.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thrashplay.spellspeaker.config.CardConfiguration;
import com.thrashplay.spellspeaker.repository.CardConfigurationRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Kleinjung
 */
@Repository
public class JsonCardConfigurationRepository implements CardConfigurationRepository {

    private static final String JSON_RESOURCE_PATH = "/com/thrashplay/spellspeaker/config/cards.json";

    private static final Gson gson = new Gson();
    private static List<CardConfiguration> cardConfigurations;

    @Override
    public CardConfiguration findOne(Long id) {
        ensureInitialized();

        if (id > 0 && id < cardConfigurations.size()) {
            return cardConfigurations.get(id.intValue());
        } else {
            return null;
        }
    }

    @Override
    public List<CardConfiguration> findAll() {
        ensureInitialized();
        return cardConfigurations;
    }

    @Override
    public <S extends CardConfiguration> S save(S entity) {
        throw new RuntimeException("JsonCardConfigurationRepository is read-only");
    }

    private synchronized void ensureInitialized() {
        if (cardConfigurations == null) {
            Reader reader = null;
            try {
                InputStream inputStream = JsonCardConfigurationRepository.class.getResourceAsStream(JSON_RESOURCE_PATH);
                reader = new InputStreamReader(inputStream);

                Type listType = new TypeToken<ArrayList<DeserializedCardConfiguration>>() {}.getType();
                List<DeserializedCardConfiguration> deserializedCardConfigurations = gson.fromJson(reader, listType);

                cardConfigurations = new ArrayList<>(deserializedCardConfigurations.size());
                for (DeserializedCardConfiguration deserializedCardConfiguration : deserializedCardConfigurations) {
                    cardConfigurations.add(deserializedCardConfiguration.toCardConfiguration());
                }
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(String.format("Failed to close reader: %s", e.toString()), e);
                    }
                }
            }
        }
    }

    private static class DeserializedCardConfiguration {
        private String name;
        private int manaCost;
        private int castingTime;
        private int quantity;

        public CardConfiguration toCardConfiguration() {
            CardConfiguration result = new CardConfiguration();
            result.setName(name);
            result.setManaCost(manaCost);
            result.setCastingTime(castingTime);
            result.setQuantity(quantity);
            return result;
        }
    }
}
