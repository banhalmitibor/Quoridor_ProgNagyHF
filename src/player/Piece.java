package player;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Piece implements Serializable{
    protected int posx;
    protected int posy;
    protected int wallnum;

    //KIMENTÉS (SZERIALIZÁCIÓ)
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        pcs = new PropertyChangeSupport(this);
    }

    public void refresh(){
        pcs.firePropertyChange("wall", -1, wallnum);
    }

    //PROPERTY CHANGE LISTENER a falszámok kiírása miatt miatt
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    public Piece(int x, int y){
        posx = x;
        posy = y;
        wallnum = 0;
        //pcs.firePropertyChange("wall", wallnum+1, wallnum);
    }

    public int getX() {return posx;}

    public int getY() {return posy;}

    public void setPos(int x, int y){
        posx = x;
        posy = y;
    }

    public boolean canPlaceWall(){
        //wallnum++;
        if(wallnum++ < 10){
            pcs.firePropertyChange("wall", wallnum-1, wallnum);
            return true;
        }
        
        return false;
    }

    public void revertWall(){
        wallnum--;
        pcs.firePropertyChange("wall", wallnum+1, wallnum);
    }

    public abstract void makeMove();

}
