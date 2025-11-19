package swingui;

import grid.GameData;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class Tile extends JButton {

    private int posx;
    private int posy;

    private static final ArrayList<Color> colors;

    public class TileClickListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            GameData.movePlayer(posx, posy);
            JButton bt = (JButton)e.getSource();
            Board b = (Board)bt.getParent();
            b.refresh();
        }
    }

    static{
        colors = new ArrayList<>();
        colors.add(new Color(200, 160, 120));
        colors.add(new Color(255, 248, 238));
        colors.add(new Color(46, 30, 22));
    }

    public Tile(int row, int col){

        posx = (col / 2);
        posy = (row / 2);

        this.addActionListener(new TileClickListener());
        this.setBackground(new Color(200, 160, 120));
        this.setContentAreaFilled(false);
        this.setFocusPainted(true);
    }

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

        //Ha éppen ezen a mezőn van egy játékos akkor kirajzoljuk a megfelelő színű kört
        int size = Math.min(getWidth(), getHeight()) - 10;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        if(getModel().isArmed()){
            g2.setColor(colors.get(GameData.playerIsOnTile(posx, posy)+1).darker());
        }
        else{
            g2.setColor(colors.get(GameData.playerIsOnTile(posx, posy)+1));
        }
        
        g2.fillOval(x, y, size, size);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }

}
