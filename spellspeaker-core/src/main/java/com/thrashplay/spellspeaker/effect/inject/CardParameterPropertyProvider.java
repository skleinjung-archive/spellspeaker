package com.thrashplay.spellspeaker.effect.inject;

import com.thrashplay.spellspeaker.effect.inject.PropertyProvider;
import com.thrashplay.spellspeaker.model.CardExecutionParameter;

/**
 * @author Sean Kleinjung
 */
public class CardParameterPropertyProvider implements PropertyProvider {
    private CardExecutionParameter parameter;
    private Object parameterValue;

    public CardParameterPropertyProvider(CardExecutionParameter parameter, String enteredValue) {
        this.parameter = parameter;
        switch (parameter.getType()) {
            case Integer:
                this.parameterValue = Integer.valueOf(enteredValue);
                break;

            case String:
                this.parameterValue = enteredValue;
                break;
        }
    }

    @Override
    public Object getProperty(String propertyName) {
        if (parameter.getName().equals(propertyName)) {
            return parameterValue;
        }
        return null;
    }
}
