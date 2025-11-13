package swingui;

import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JPanel;


public class GameFrame extends JFrame {

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
