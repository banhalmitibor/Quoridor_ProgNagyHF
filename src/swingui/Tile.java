package swingui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

import game.Controller;

/**
 * Button representing a single tile on the game board.
 * Players can click tiles to move their pieces. The tile displays
 * a colored circle when a player is standing on it.
 * 
 * @author Quoridor Team
 * @version 1.0
 */
public class Tile extends JButton {

    /** The x-coordinate of this tile on the game board. */
    private final int posx;
    
    /** The y-coordinate of this tile on the game board. */
    private final int posy;

    /** Color palette for player pieces and empty tiles. */
    private static final ArrayList<Color> colors;

    /**
     * Action listener that handles tile click events.
     * Attempts to move the current player to this tile.
     */
    public class TileClickListener implements ActionListener{
        /**
         * Invoked when the tile is clicked.
         * Delegates the move action to the game controller.
         * 
         * @param e the action event
         */
        @Override
        public void actionPerformed(ActionEvent e){
            Controller.movePlayer(posx, posy);
        }
    }

    static{
        colors = new ArrayList<>();
        colors.add(new Color(200, 160, 120));
        colors.add(new Color(255, 248, 238));
        colors.add(new Color(46, 30, 22));
        colors.add(new Color(0, 255, 0));
        colors.add(new Color(0, 0, 255));
    }

    /**
     * Constructs a new Tile at the specified grid position.
     * Converts from visual grid coordinates to game coordinates.
     * 
     * @param row the visual grid row (even numbers only)
     * @param col the visual grid column (even numbers only)
     */
    public Tile(int row, int col){

        posx = (col / 2);
        posy = (row / 2);

        this.addActionListener(new TileClickListener());

        Controller.addPropertyChangeListener(evt ->{
            String n = evt.getPropertyName();
            if(n.equals("Tile")){
                this.repaint();
            }
        });

        this.setBackground(new Color(200, 160, 120));
        this.setContentAreaFilled(false);
        this.setFocusPainted(true);
    }

    /**
     * Custom paint method to render the tile with rounded corners
     * and display player pieces as colored circles.
     * 
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isArmed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Draw player piece if present on this tile
        int size = Math.min(getWidth(), getHeight()) - 10;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if(getModel().isArmed()){
            g2.setColor(colors.get(Controller.playerIsOnTile(posx, posy)+1).darker());
        }
        else{
            g2.setColor(colors.get(Controller.playerIsOnTile(posx, posy)+1));
        }
        
        g2.fillOval(x, y, size, size);

        super.paintComponent(g);
        g2.dispose();
    }

    /**
     * Custom border painting with rounded corners.
     * 
     * @param g the Graphics context
     */
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }

}
