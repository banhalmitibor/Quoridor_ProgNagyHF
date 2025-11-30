package swingui;

import java.awt.*;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.Controller;

public class Wall extends JButton {

    private final int posx;
    private final int posy;
    private final boolean vertical;

    private static final Color NOT_PLACED = new Color(240, 225, 200);
    private static final Color PLACED = new Color(140, 105,80);

    public class MouseHoverListener extends MouseAdapter{
        @Override 
        public void mouseEntered(MouseEvent e){
            //System.out.println("ENTERED");
            Wall w = (Wall)e.getSource();
            w.setBackground(new Color(128, 128, 128, 64));
        }
        @Override
        public void mouseExited(MouseEvent e){
            //System.out.println("EXITED");
            Wall w = (Wall)e.getSource();
            w.refreshColor();
        }
    }

    public class ClickWall implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            
            Controller.placeWall(posx, posy, vertical);
            /*refreshColor();
            JButton bt = (JButton)e.getSource();
            Board b = (Board)bt.getParent();
            b.refresh();*/
        }
    }

    public Wall(int row, int col){
        
        Color c;
        if(row % 2 == 0){
            vertical = true;
            posx = (col / 2);
            posy = (row / 2);
            //System.out.println(posx + " " + posy);
            if(posy < 8) c = Controller.getVertcialWalls().get(posx).get(posy) || Controller.getVertcialWalls().get(posx).get(posy-1>0?posy-1:0) ? PLACED : NOT_PLACED;
            else c = Controller.getVertcialWalls().get(posx).get(posy-1) ? PLACED : NOT_PLACED;
        }
        else{
            vertical = false;
            posx = (col / 2);
            posy = (row / 2);
            //String st = posx == 7 && posy == 6 ? " KIVALASZTVA" : "";
            System.out.println(posx + " " + posy);
            if(posx < 8) c = Controller.getHorizontalWalls().get(posx).get(posy) || Controller.getHorizontalWalls().get(posx-1>0?posx-1:0).get(posy) ? PLACED : NOT_PLACED;
            else c = Controller.getHorizontalWalls().get(posx-1).get(posy) ? PLACED : NOT_PLACED;
        }

        this.addActionListener(new ClickWall());
        this.addMouseListener(new MouseHoverListener());
        Controller.addPropertyChangeListener(evt ->{
            String n = evt.getPropertyName();
            if(n.equals("Wall")){
                refreshColor();
            }
        });

        this.setOpaque(true);
        this.setBackground(c);
        this.setFocusable(false);
        this.setBorder(null);
    }

    public void refreshColor(){
        Color c;
        if(vertical){
            //System.out.println(posx + " " + posy);
            if(posy < 8) c = Controller.getVertcialWalls().get(posx).get(posy) || Controller.getVertcialWalls().get(posx).get(posy-1>0?posy-1:0) ? PLACED : NOT_PLACED;
            else c = Controller.getVertcialWalls().get(posx).get(posy-1) ? PLACED : NOT_PLACED;
        }
        else{
            //String st = posx == 7 && posy == 6 ? " KIVALASZTVA" : "";
            //System.out.println(posx + " " + posy);
            if(posx < 8) c = Controller.getHorizontalWalls().get(posx).get(posy) || Controller.getHorizontalWalls().get(posx-1>0?posx-1:0).get(posy) ? PLACED : NOT_PLACED;
            else c = Controller.getHorizontalWalls().get(posx-1).get(posy) ? PLACED : NOT_PLACED;
        }
        this.setBackground(c);
    }

}
