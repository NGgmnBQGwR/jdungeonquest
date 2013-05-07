package jdungeonquest;

//Stuff to add:
//isConnected
//players ID given from server to differentiate clients
//convinient data structure to hold all known stuff about player
public class Player {

    private String name;
    private String classname;
    private int gold;
    private int hp;
    private Position position = new Position();

    public int searchInRow = 0; //player may search a room only 2 times in a row
    
    public boolean moved = false;
    public boolean placedTile = false;
    public boolean searched = false;
    public boolean didSomething = false; //"a player may do absolutely nothing while missing a turn"
    
    public Player() {
    }

    public Player(String n) {
        this.name = n;
    }

    public void resetTurnVariables(){
        moved = false;
        placedTile = false;
        searched = false;
        didSomething = false;
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
}
