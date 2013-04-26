package jdungeonquest;

//Stuff to add:
//isConnected
//players ID given from server to differentiate clients
//convinient data structure to hold all known stuff about player
public class Player {

    private String name;
    private int gold;
    private int hp;

    public Player() {
    }

    public Player(String n) {
        this.name = n;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
