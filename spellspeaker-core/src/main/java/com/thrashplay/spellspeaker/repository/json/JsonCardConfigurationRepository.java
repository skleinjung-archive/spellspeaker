package com.thrashplay.spellspeaker.repository.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thrashplay.spellspeaker.SpellspeakerException;
import com.thrashplay.spellspeaker.config.CardConfiguration;
import com.thrashplay.spellspeaker.config.GameRules;
import com.thrashplay.spellspeaker.effect.SpellEffect;
import com.thrashplay.spellspeaker.effect.spell.Noop;
import com.thrashplay.spellspeaker.model.CardType;
import com.thrashplay.spellspeaker.model.Element;
import com.thrashplay.spellspeaker.repository.CardConfigurationRepository;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
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
    private static List<CardConfiguration> baseCardConfigurations;
    private static List<CardConfiguration> libraryCardConfigurations;

    private GameRules rules;

    @Autowired
    public JsonCardConfigurationRepository(GameRules rules) {
        this.rules = rules;
    }

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

        List<CardConfiguration> libraryCards = findAllLibraryCards();
        List<CardConfiguration> baseCards = findAllBaseCards();
        List<CardConfiguration> result = new ArrayList<>(libraryCards.size() + baseCards.size());
        result.addAll(libraryCards);
        result.addAll(baseCards);
        return result;
    }

    @Override
    public List<CardConfiguration> findAllBaseCards() {
        ensureInitialized();
        return baseCardConfigurations;
    }

    @Override
    public List<CardConfiguration> findAllLibraryCards() {
        ensureInitialized();
        return libraryCardConfigurations;
    }

    @Override
    public <S extends CardConfiguration> S save(S entity) {
        throw new RuntimeException("JsonCardConfigurationRepository is read-only");
    }

    private synchronized void ensureInitialized() {
        if (libraryCardConfigurations == null || baseCardConfigurations == null) {
            Reader reader = null;
            try {
                InputStream inputStream = JsonCardConfigurationRepository.class.getResourceAsStream(JSON_RESOURCE_PATH);
                reader = new InputStreamReader(inputStream);

                Type listType = new TypeToken<DeserializedCardConfigurations>() {}.getType();
                DeserializedCardConfigurations deserializedCardConfigurations = gson.fromJson(reader, listType);

                baseCardConfigurations = new ArrayList<>(deserializedCardConfigurations.baseCards.size());
                for (DeserializedCardConfiguration deserializedCardConfiguration : deserializedCardConfigurations.baseCards) {
                    baseCardConfigurations.add(deserializedCardConfiguration.toCardConfiguration(rules));
                }

                libraryCardConfigurations = new ArrayList<>(deserializedCardConfigurations.library.size());
                for (DeserializedCardConfiguration deserializedCardConfiguration : deserializedCardConfigurations.library) {
                    libraryCardConfigurations.add(deserializedCardConfiguration.toCardConfiguration(rules));
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
        private List<DeserializedCardConfiguration> baseCards;
        private List<DeserializedCardConfiguration> library;

        public List<DeserializedCardConfiguration> getBaseCards() {
            return baseCards;
        }

        public void setBaseCards(List<DeserializedCardConfiguration> baseCards) {
            this.baseCards = baseCards;
        }

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
        private boolean reusable;
        private int manaCost;
        private int castingTime;
        private int quantity;
        private String element;
        private int power;
        private String text;
        private List<String> textVariables;
        private String effect;

        public CardConfiguration toCardConfiguration(GameRules rules) {
            CardConfiguration result = new CardConfiguration();
            result.setName(name);

            if (type == null) {
                type = "Rune";
            }
            result.setType(CardType.fromName(type));

            result.setReusable(reusable);

            result.setManaCost(manaCost);
            result.setCastingTime(castingTime);

            if (quantity == 0) {
                quantity = 1;
            }
            result.setQuantity(quantity);

            result.setElement(element == null ? Element.None : Element.fromName(element));
            result.setPower(power);
            result.setText(resolveText(rules));

            if (effect == null) {
                effect = Noop.class.getName();
            }
            try {
                result.setEffect((SpellEffect) Class.forName(effect).newInstance());
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ClassCastException e) {
                throw new CardConfigurationException(String.format("Invalid effect class for card '%s': %s", name, effect), e);
            }

            return result;
        }

        private String resolveText(GameRules rules) {
            String result = text;
            if (textVariables != null && textVariables.size() > 0) {
                Object[] parameters = new Object[textVariables.size()];
                for (int i = 0; i < textVariables.size(); i++) {
                    try {
                        parameters[i] = PropertyUtils.getProperty(rules, textVariables.get(i));
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new CardConfigurationException("Invalid textVariable name: " + textVariables.get(i), e);
                    }
                }

                result = String.format(text, parameters);
            }

            return result;
        }
    }
}
