package swingui;

import grid.GameData;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Controller;

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

        //this.add(new JButton());
    }
    
}
