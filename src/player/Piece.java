package player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Abstract base class representing a game piece (player) in the Quoridor game.
 * Handles position tracking, wall placement count, and property change notifications.
 * Implements Serializable for game save/load functionality.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public abstract class Piece implements Serializable{
    /** The x-coordinate position of the piece on the board. */
    protected int posx;
    
    /** The y-coordinate position of the piece on the board. */
    protected int posy;
    
    /** The number of walls this player has placed. */
    protected int wallnum;

    /**
     * Custom serialization method to write the object state.
     * 
     * @param out the ObjectOutputStream to write to
     * @throws IOException if an I/O error occurs
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Custom deserialization method to restore the object state.
     * Reinitializes the PropertyChangeSupport after deserialization.
     * 
     * @param in the ObjectInputStream to read from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class cannot be found
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        pcs = new PropertyChangeSupport(this);
    }

    /**
     * Refreshes the property change listeners with current wall count.
     * Called after loading a saved game to update the UI.
     */
    public void refresh(){
        pcs.firePropertyChange("wall", -1, wallnum);
    }

    /** Property change support for notifying listeners about wall count changes. */
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Adds a property change listener to this piece.
     * 
     * @param l the PropertyChangeListener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    /**
     * Removes a property change listener from this piece.
     * 
     * @param l the PropertyChangeListener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    /**
     * Constructs a new Piece at the specified position.
     * Initializes wall count to 0.
     * 
     * @param x the initial x-coordinate
     * @param y the initial y-coordinate
     */
    public Piece(int x, int y){
        posx = x;
        posy = y;
        wallnum = 0;
    }

    /**
     * Gets the x-coordinate of this piece.
     * 
     * @return the x-coordinate
     */
    public int getX() {return posx;}

    /**
     * Gets the y-coordinate of this piece.
     * 
     * @return the y-coordinate
     */
    public int getY() {return posy;}

    /**
     * Sets the position of this piece.
     * 
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void setPos(int x, int y){
        posx = x;
        posy = y;
    }

    /**
     * Attempts to place a wall for this player.
     * Each player can place a maximum of 10 walls.
     * 
     * @return true if the wall can be placed (player has walls remaining), false otherwise
     */
    public boolean canPlaceWall(){
        if(wallnum++ < 10){
            pcs.firePropertyChange("wall", wallnum-1, wallnum);
            return true;
        }
        
        return false;
    }

    /**
     * Reverts the last wall placement.
     * Called when a wall placement is invalid and needs to be undone.
     */
    public void revertWall(){
        wallnum--;
        pcs.firePropertyChange("wall", wallnum+1, wallnum);
    }

    /**
     * Abstract method for making a move.
     * Subclasses must implement their own move logic.
     */
    public abstract void makeMove();

}
