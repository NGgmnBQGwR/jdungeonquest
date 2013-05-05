
package jdungeonquest;

public class Position {
    private int x;
    private int y;
    
    public Position(){
        x = 0;
        y = 0;
    }
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if(o == this){
            return true;
        }
        if(! (o instanceof Position) ){
            return false;
        }
        Position other = (Position)o;
        if( other.x != this.x){
            return false;
        }
        if( other.y != this.y){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public String toString(){
        return "[x:" + x + " y:" + y + "]";
    }
}
