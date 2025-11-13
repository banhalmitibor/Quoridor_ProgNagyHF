package swingui;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

public class Board extends JPanel{
    
    public Board(){
        this.setBounds(50, 50, 626, 620);
        this.setBackground(new Color(240, 225, 200));
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();


        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        gbc.fill = GridBagConstraints.BOTH;

        gbc.insets = new Insets(0, 0, 0, 0);


        for (int row = 0; row < 17; row++) {
            for(int col = 0; col < 17; col++){
                if(row % 2 == 0){
                    if(col % 2 == 1){
                        gbc.insets = new Insets(0, 0, 0, 0);
                        gbc.weightx = 0.07;
                        gbc.weighty = 1.0;
                        gbc.gridx = col;
                        gbc.gridy = row;
                        
                        this.add(new Wall(row, col), gbc);
                    }
                    else{
                        gbc.insets = new Insets(2, 2, 2, 2);
                        gbc.weightx = 1.0;
                        gbc.weighty = 1.0;
                        gbc.gridx = col;
                        gbc.gridy = row;
                        this.add(new Tile(), gbc);
                    }
                }
                else{
                    if(col % 2 == 0){
                        gbc.insets = new Insets(0, 0, 0, 0);
                        gbc.weightx = 1.0;
                        gbc.weighty = 0.07;
                        gbc.gridx = col;
                        gbc.gridy = row;
                        
                        this.add(new Wall(row, col), gbc);
                    }
                    else{
                        gbc.insets = new Insets(0, 0, 0, 0);
                        gbc.weightx = 0.07;
                        gbc.weighty = 0.07;
                        gbc.gridx = col;
                        gbc.gridy = row;
                        
                        this.add(new Intersection(row, col), gbc);
                    }
                }
                
            }

        }
        
        
    }

    public void refresh(){       
        for(Component e : this.getComponents()){
            if(e.getClass() == Wall.class){
                Wall w = (Wall)e;
                w.refreshColor();
            }
            else if(e.getClass() == Intersection.class){
                Intersection i = (Intersection)e;
                i.refreshColor();
            }
        } 
    }

}
