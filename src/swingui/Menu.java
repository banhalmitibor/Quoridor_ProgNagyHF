package swingui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Controller;
import javax.swing.JPopupMenu;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

/**
 * Menu panel containing game controls and player information.
 * Provides buttons for starting new games, saving and loading game states,
 * and displays wall counts for each player.
 * 
 * @author Quoridor Team
 * @version 1.0
 */
public class Menu extends JPanel {
    
    /**
     * Constructs a new Menu panel with all control elements.
     * Includes wall count displays, new game buttons, and save/load controls.
     */
    public Menu(){
        this.setBounds(726, 50, 400, 620);
        this.setBackground(Color.GRAY);
        this.setLayout(new BorderLayout());
        

        // Wall count displays for each player
        JPanel walldisplays = new JPanel();
        walldisplays.setLayout(new BoxLayout(walldisplays, BoxLayout.Y_AXIS));
        for(int i = 0; i < 4; i++){
            JLabel l = new WallDisplay(i);
            walldisplays.add(l);
        }
        this.add(walldisplays, BorderLayout.NORTH);

        // New game buttons
        JPanel middlePanel = new JPanel();
        JButton newBut2 = new JButton("New 2 player game");
        JButton newBut4 = new JButton("New 4 player game");

        newBut2.addActionListener(ae -> {
            Controller.reset(2);
        });
        newBut4.addActionListener(ae -> {
            Controller.reset(4);
        });

        middlePanel.add(newBut2);
        middlePanel.add(newBut4);
        this.add(middlePanel, BorderLayout.CENTER);

        // Save/Load controls
        JPanel savePanel = new JPanel();
        JComboBox<Integer> saveChooser = new JComboBox<>(new Integer[] {1, 2, 3, 4, 5});
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(evt -> {
            try{
                Controller.saveGame((int)saveChooser.getSelectedItem());
            }
            catch(Exception e){System.out.println(e.getMessage());}
        });
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(evt -> {
            try{
                Controller.loadGame((int)saveChooser.getSelectedItem());
            }
            catch(Exception e){System.out.println(e.getMessage());}
        });
        
        savePanel.add(saveChooser); 
        savePanel.add(saveButton);
        savePanel.add(loadButton);

        this.add(savePanel, BorderLayout.SOUTH);

        


    }
    
}
