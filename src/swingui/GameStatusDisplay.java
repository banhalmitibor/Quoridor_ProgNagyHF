package swingui;

import java.awt.Color;

import javax.swing.JLabel;
import grid.GameData;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameStatusDisplay extends JLabel{

   

    public GameStatusDisplay(){
        this.setBounds(265, 20, 250, 20);
        this.setFont(this.getFont().deriveFont(20f));
        this.setForeground(Color.WHITE);
        this.setText(GameData.curPlayer+1 + ". játékos következik");


        GameData.addPropertyChangeListener(evt -> {
            String n = evt.getPropertyName();
            System.out.println("EVENT name = " + n);
            if(n.equals("Player")){
                System.out.println("PLAYER CHANGE");
                this.setText((int)evt.getNewValue()+1 + ". játékos következik");
            }
            else if (n.equals("Win")) {
                System.out.println("WIN CHANGE");

                this.setText(evt.getNewValue() + ". játékos nyert!");
            }
        });
        
    }
}
