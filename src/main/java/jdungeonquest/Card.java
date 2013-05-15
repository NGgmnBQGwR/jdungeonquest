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
        StringBuilder sb = new StringBuilder();
        
        sb.append(deckType);
        sb.append(" Card: ");
        sb.append(description);
        sb.append(", ");
        
        if (!effects.isEmpty()){
            sb.append(" Effects:");
            for (Effect s : effects) {
                sb.append(s);
                sb.append("/");
            }
            sb.setLength(sb.length()-1);
        }
        return sb.toString();
    }    
}
