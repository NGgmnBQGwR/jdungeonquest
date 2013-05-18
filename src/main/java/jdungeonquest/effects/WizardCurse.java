package jdungeonquest.effects;

import jdungeonquest.Game;

public class WizardCurse implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectWizardCurse();
    }    
}
