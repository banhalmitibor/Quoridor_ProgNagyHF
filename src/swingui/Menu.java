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

public class Menu extends JPanel {
    public Menu(){
        this.setBounds(726, 50, 400, 620);
        this.setBackground(Color.GRAY);
        this.setLayout(new BorderLayout());
        

        //FALAK SZÁMÁNAK KIÍRÁSA
        JPanel walldisplays = new JPanel();
        walldisplays.setLayout(new BoxLayout(walldisplays, BoxLayout.Y_AXIS));
        for(int i = 0; i < 4; i++){
            //System.out.println("PLAYER LABEL CREATED " + i);
            JLabel l = new WallDisplay(i);
            walldisplays.add(l);
        }
        this.add(walldisplays, BorderLayout.NORTH);

        //Új játék gomb
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

        


        //this.add(new JButton());
    }
    
}
