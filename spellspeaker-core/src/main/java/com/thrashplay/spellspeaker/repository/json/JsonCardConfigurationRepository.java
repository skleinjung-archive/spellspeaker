package com.thrashplay.spellspeaker.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thrashplay.spellspeaker.SpellspeakerException;
import com.thrashplay.spellspeaker.config.CardConfiguration;
import com.thrashplay.spellspeaker.model.CardType;
import com.thrashplay.spellspeaker.model.Element;
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
    private static List<CardConfiguration> libraryCardConfigurations;

    @Override
    public CardConfiguration findOne(Long id) {
        ensureInitialized();

        if (id > 0 && id < libraryCardConfigurations.size()) {
            return libraryCardConfigurations.get(id.intValue());
        } else {
            return null;
        }
    }

    @Override
    public List<CardConfiguration> findAll() {
        ensureInitialized();
        return libraryCardConfigurations;
    }

    @Override
    public <S extends CardConfiguration> S save(S entity) {
        throw new RuntimeException("JsonCardConfigurationRepository is read-only");
    }

    private synchronized void ensureInitialized() {
        if (libraryCardConfigurations == null) {
            Reader reader = null;
            try {
                InputStream inputStream = JsonCardConfigurationRepository.class.getResourceAsStream(JSON_RESOURCE_PATH);
                reader = new InputStreamReader(inputStream);

                Type listType = new TypeToken<DeserializedCardConfigurations>() {}.getType();
                DeserializedCardConfigurations deserializedCardConfigurations = gson.fromJson(reader, listType);

                libraryCardConfigurations = new ArrayList<>(deserializedCardConfigurations.library.size());
                for (DeserializedCardConfiguration deserializedCardConfiguration : deserializedCardConfigurations.library) {
                    libraryCardConfigurations.add(deserializedCardConfiguration.toCardConfiguration());
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

    private static class DeserializedCardConfigurations {
        private List<DeserializedCardConfiguration> library;

        public List<DeserializedCardConfiguration> getLibrary() {
            return library;
        }

        public void setLibrary(List<DeserializedCardConfiguration> library) {
            this.library = library;
        }
    }

    private static class DeserializedCardConfiguration {
        private String name;
        private String type;
        private int manaCost;
        private int castingTime;
        private int quantity;
        private String element;
        private int power;
        private String text;

        public CardConfiguration toCardConfiguration() {
            CardConfiguration result = new CardConfiguration();
            result.setName(name);

            if (type == null) {
                type = "Rune";
            }
            result.setType(CardType.fromName(type));

            result.setManaCost(manaCost);
            result.setCastingTime(castingTime);
            result.setQuantity(quantity);
            result.setElement(element == null ? Element.None : Element.fromName(element));
            result.setPower(power);
            result.setText(text);
            return result;
        }
    }
}
