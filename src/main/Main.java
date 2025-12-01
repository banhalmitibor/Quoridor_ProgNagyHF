package main;

import grid.GameData;
import swingui.GameFrame;
import game.Controller;

/**
 * Main entry point for the Quoridor game application.
 * Initializes the game data, sets up the controller, and creates the game window.
 * 
 * @author Quoridor Team
 * @version 1.0
 */
public class Main {
    
    /**
     * Application entry point.
     * Creates and initializes the game components and displays the game window.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        GameData data = new GameData();
        Controller.setData(data);

        GameFrame frame = new GameFrame();
        


    }

}
