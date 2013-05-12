package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.enums.MonsterType;

public class MeetMonster implements Effect {

    private MonsterType type;

    public MeetMonster(){
        
    }
    
    public MeetMonster(MonsterType type) {
        this.type = type;
    }

    @Override
    public void doAction(Game game) {
        game.startMonsterCombat(getType());
    }

    @Override
    public String toString() {
        return "MonsterEffect " + getType();
    }

    /**
     * @return the type
     */
    public MonsterType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(MonsterType type) {
        this.type = type;
    }

}
