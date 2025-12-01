package swingui;

import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Main game window frame for the Quoridor game.
 * Contains the game board, status display, and menu panel.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class GameFrame extends JFrame {

    /**
     * Constructs a new GameFrame with all UI components.
     * Sets up the window with the game board, status display, and menu panel.
     * The window is fixed size (1280x720) and non-resizable.
     */
    public GameFrame(){

        this.setTitle("Quoridor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1280, 720);
        this.setLayout(null);
        
        JLabel gameStatus = new GameStatusDisplay();
        JPanel board = new Board();

        this.getContentPane().setBackground(new Color(120, 30, 160));
        this.add(board);
        this.add(gameStatus);

        JPanel menu = new Menu();
        this.add(menu);

        this.setVisible(true);
        
    }
  
}
