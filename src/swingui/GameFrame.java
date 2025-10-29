package swingui;

import javax.swing.JFrame;
import java.awt.Color;

public class GameFrame extends JFrame {
    

    public GameFrame(){

        this.setTitle("Quoridor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(420, 420);
        this.setVisible(true);


        this.getContentPane().setBackground(new Color(120, 30, 160));
    }
}
