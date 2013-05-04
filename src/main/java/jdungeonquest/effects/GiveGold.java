package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.enums.PlayerAttributes;

public class GiveGold implements Effect {

    private int amount;

    public GiveGold(){
        
    }
    
    public GiveGold(int amount) {
        this.amount = amount;
    }

    @Override
    public void doAction(Game game) {
//        int currentGold = game.GetPlayerAttribute(game.getCurrentPlayer(), PlayerAttributes.Gold);
//        game.changePlayerAttribute(game.getCurrentPlayer(), PlayerAttributes.Gold, currentGold + getAmount());
    }

    @Override
    public String toString() {
        return "GiveGold " + getAmount();
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
