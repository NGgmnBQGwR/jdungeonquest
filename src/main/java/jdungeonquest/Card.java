package jdungeonquest;

import java.util.ArrayList;
import java.util.List;
import jdungeonquest.effects.Effect;
import jdungeonquest.enums.DeckType;

public class Card {

    private String description;
    private DeckType deckType;
    private List<Effect> effects;

    public Card() {

    }

    public void activate(Game game) {
        for (Effect e : getEffects()) {
            e.doAction(game);
        }
    }

    public DeckType getDeckType() {
        return deckType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param deckType the deckType to set
     */
    public void setDeckType(DeckType deckType) {
        this.deckType = deckType;
    }

    /**
     * @return the effects
     */
    public List<Effect> getEffects() {
        return effects;
    }

    /**
     * @param effects the effects to set
     */
    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }
    
    @Override
    public String toString() {
        String effectss = effects.isEmpty()?"":" Effects:";
        for(Effect s : effects){
            effectss += s;
            effectss += " / ";
        }
        return new String(description + ", " + deckType + (effects.isEmpty()?"":", "+effects.size()+effectss));
    }    
}
