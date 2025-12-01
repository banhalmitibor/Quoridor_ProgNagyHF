package swingui;

import java.awt.Color;

import javax.swing.JLabel;

import game.Controller;

/**
 * Label component that displays the current game status.
 * Shows which player's turn it is and announces the winner.
 * Updates automatically via property change listeners.
 * 
 * @author Quoridor Team
 * @version 1.0
 */
public class GameStatusDisplay extends JLabel{

   
    /**
     * Constructs a new GameStatusDisplay label.
     * Initializes the display with the current player's turn and
     * registers listeners for player change and win events.
     */
    public GameStatusDisplay(){
        this.setBounds(265, 20, 250, 20);
        this.setFont(this.getFont().deriveFont(20f));
        this.setForeground(Color.WHITE);
        this.setText(Controller.getCurPlayer()+1 + ". játékos következik");


        Controller.addPropertyChangeListener(evt -> {
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
