package swingui;

import java.awt.Color;
import javax.swing.JPanel;

import grid.GameData;

public class Intersection extends JPanel {

    private int posx; //1 től indulnak
    private int posy;

    public Intersection(int row, int col){
        posx = (col/2) + 1;
        posy = (row/2) + 1;

        GameData.addPropertyChangeListener(evt ->{
            String n = evt.getPropertyName();
            if(n.equals("Wall")){
                refreshColor();
            }
        });

        System.out.println("INTERSECTION POS: " + posx + " " + posy);
        this.setBackground(new Color(0, true));
    }

    public void refreshColor(){
        Color c;
        
        c = GameData.horizontalWalls.get(posx-1).get(posy-1) || GameData.verticalWalls.get(posx-1).get(posy-1) ? new Color(140, 105,80) : new Color(240, 225, 200);
        
        this.setBackground(c);
    }
}
