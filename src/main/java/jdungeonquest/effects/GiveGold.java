package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.enums.PlayerAttributes;
import jdungeonquest.network.ChatMessage;

public class GiveGold implements Effect {

    private int amount;

    public GiveGold(){
        
    }
    
    public GiveGold(int amount) {
        this.amount = amount;
    }

    @Override
    public void doAction(Game game) {
        int newGold = game.currentPlayer.getGold() + amount;
        if(amount<10){
            game.addMessage(new ChatMessage("You have found a few coins worth " + amount + " gold.", "Game"));
        }else if(amount<50){
            game.addMessage(new ChatMessage("You have found a pouch of coins worth " + amount + " gold.", "Game"));
        }else if(amount<100){
            game.addMessage(new ChatMessage("You have found a piece of jewelry worth " + amount + " gold.", "Game"));
        }else if(amount<500){
            game.addMessage(new ChatMessage("You have found a pouch, filled with precious gems! Worth " + amount + " gold.", "Game"));
        }else if(amount<1000){
            game.addMessage(new ChatMessage("You have found a golden diamond-encrusted crown! Worth " + amount + " gold.", "Game"));
        }else{
            game.addMessage(new ChatMessage("You have found a chest, filled with gold! Total worth " + amount + " gold.", "Game"));
        }
        game.changePlayerAttribute(game.currentPlayer, PlayerAttributes.Gold, newGold);
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
