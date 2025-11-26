package swingui;

import javax.swing.JLabel;

import grid.GameData;

public class WallDisplay extends JLabel {

    private int idx;

    public WallDisplay(int i){
        idx = i+1;
        this.setText(idx + ". játékos falai: " + 10);
        GameData.players.get(idx-1).addPropertyChangeListener(evt -> {
            String n = evt.getPropertyName();
            //System.out.println("EVENT name = " + n);
            if(n.equals("wall")){
                //System.out.println("PLAYER CHANGE");
                this.setText(idx + ". játékos falai: " + (10-(int)evt.getNewValue()));
            }
        });

        GameData.addPropertyChangeListener(evt -> {
            String n = evt.getPropertyName();
            //System.out.println("EVENT name = " + n);
            if(n.equals("Reset")){
                this.setText(idx + ". játékos falai: " + 10);
                GameData.players.get(idx-1).addPropertyChangeListener(ev -> {
                    String np = ev.getPropertyName();
                    //System.out.println("EVENT name = " + n);
                    if(np.equals("wall")){
                        //System.out.println("PLAYER CHANGE");
                        this.setText(idx + ". játékos falai: " + (10-(int)ev.getNewValue()));
                    }
                });
            }
        });
    }
}
