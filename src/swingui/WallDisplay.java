package swingui;

import javax.swing.JLabel;

import game.Controller;

/**
 * Label component that displays the remaining wall count for a player.
 * Each player starts with 10 walls and the count decreases as walls are placed.
 * Updates automatically via property change listeners.
 * 
 * @author banhalmitibor
 * @version 1.0
 */
public class WallDisplay extends JLabel {

    /** The 1-based index of the player this display represents. */
    private int idx;

    /**
     * Constructs a new WallDisplay for the specified player.
     * Registers listeners for wall placement and game reset events.
     * 
     * @param i the 0-based player index
     */
    public WallDisplay(int i){
        idx = i+1;
        this.setText(idx + ". játékos falai: " + 10);
        if(idx-1 < Controller.getPlayers().size()){
            Controller.getPlayers().get(idx-1).addPropertyChangeListener(evt -> {
                String n = evt.getPropertyName();
                if(n.equals("wall")){
                    this.setText(idx + ". játékos falai: " + (10-(int)evt.getNewValue()));
                }
            });
        }
        else{
            this.setText("");
        }

        Controller.addPropertyChangeListener(evt -> {
            String n = evt.getPropertyName();
            if(n.equals("Reset")){
                this.setText(idx + ". játékos falai: " + 10);
                if(idx-1 < Controller.getPlayers().size()) {
                    Controller.getPlayers().get(idx-1).addPropertyChangeListener(ev -> {
                        String np = ev.getPropertyName();
                        if(np.equals("wall")){
                            this.setText(idx + ". játékos falai: " + (10-(int)ev.getNewValue()));
                        }
                    });
                }
                else{
                    this.setText("");
                }
            }
        });
    }
}
