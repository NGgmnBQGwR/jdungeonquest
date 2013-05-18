package jdungeonquest;

//Stuff to add:

import jdungeonquest.enums.PlayerStatus;

//isConnected
//players ID given from server to differentiate clients
//convinient data structure to hold all known stuff about player
public class Player {

    private String name;
    private String classname;
    private int gold = 0;
    private int hp = 0;
    private Position previousPosition = new Position();
    private Position position = new Position();

    public int searchInRow = 0; //player may search a room only 2 times in a row
    public int turnsToSkip = 0;
    public String turnsSkipReason = "";
    public PlayerStatus status = PlayerStatus.NONE;
    
    public int agility = 7;
    public int armor = 5;
    public int luck = 7;
    
    private boolean moved = false;
    private boolean placedTile = false;
    private boolean searched = false;
    private boolean didSomething = false; //"a player may do absolutely nothing while missing a turn"
    private boolean dead = false;
    
    public Player() {
    }

    public Player(String n) {
        this.name = n;
    }

    public void resetTurnVariables(){
        setMoved(false);
        setPlacedTile(false);
        setSearched(false);
        setDidSomething(false);
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * @return the classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param classname the classname to set
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the moved
     */
    public boolean isMoved() {
        return moved;
    }

    /**
     * @param moved the moved to set
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
        if(moved){
            setDidSomething(true);
        }
    }

    /**
     * @return the placedTile
     */
    public boolean isPlacedTile() {
        return placedTile;
    }

    /**
     * @param placedTile the placedTile to set
     */
    public void setPlacedTile(boolean placedTile) {
        this.placedTile = placedTile;
        if(placedTile){
            setDidSomething(true);
        }
    }

    /**
     * @return the searched
     */
    public boolean isSearched() {
        return searched;
    }

    /**
     * @param searched the searched to set
     */
    public void setSearched(boolean searched) {
        this.searched = searched;
        if(searched){
            setDidSomething(true);
        }
    }

    /**
     * @return the didSomething
     */
    public boolean isDidSomething() {
        return didSomething;
    }

    /**
     * @param didSomething the didSomething to set
     */
    public void setDidSomething(boolean didSomething) {
        this.didSomething = didSomething;
    }

    /**
     * @return the dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * @param dead the dead to set
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * @return the gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * @param gold the gold to set
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * @return the hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @param hp the hp to set
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @return the previousPosition
     */
    public Position getPreviousPosition() {
        return previousPosition;
    }

    /**
     * @param previousPosition the previousPosition to set
     */
    public void setPreviousPosition(Position previousPosition) {
        this.previousPosition = previousPosition;
    }
}
