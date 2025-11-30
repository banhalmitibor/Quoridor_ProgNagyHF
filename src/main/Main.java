package main;

import grid.GameData;
import swingui.GameFrame;
import game.Controller;

public class Main {
    
    public static void main(String[] args) {

        GameData data = new GameData();
        Controller.setData(data);

        GameFrame frame = new GameFrame();


    }

}
