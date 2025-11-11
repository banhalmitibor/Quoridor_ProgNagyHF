package swingui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


public class GameFrame extends JFrame {
    
    JButton button = new JButton();

    public GameFrame(){

        this.setTitle("Quoridor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);
        this.setLayout(null);
        

        JPanel board = new Board();

        this.getContentPane().setBackground(new Color(120, 30, 160));
        this.add(board);

        this.setVisible(true);
        
    }
  
}
