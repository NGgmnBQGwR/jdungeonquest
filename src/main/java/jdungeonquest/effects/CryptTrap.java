package jdungeonquest.effects;

import jdungeonquest.Game;

public class CryptTrap implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectCryptTrap();
    }
}
