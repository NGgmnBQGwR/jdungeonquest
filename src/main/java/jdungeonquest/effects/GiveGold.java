package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.enums.PlayerAttributes;

public class GiveGold implements Effect {

    private int amount;

    public GiveGold(int amount) {
        this.amount = amount;
    }

    @Override
    public void doAction(Game game) {
        int currentGold = game.GetPlayerAttribute(game.getCurrentPlayer(), PlayerAttributes.Gold);
        game.changePlayerAttribute(game.getCurrentPlayer(), PlayerAttributes.Gold, currentGold + amount);
        game.broadCast("Player " + game.getCurrentPlayer() + " recieved bonus of " + new Integer(amount).toString() + " gold!");
    }
}
