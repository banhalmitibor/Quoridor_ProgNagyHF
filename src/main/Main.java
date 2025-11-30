package main;

import grid.GameData;
import javax.swing.*;
import swingui.GameFrame;
import game.Controller;
import java.util.ResourceBundle;

public class Main {
    
    public static void main(String[] args) {

        GameData data = new GameData();
        Controller.setData(data);

        GameFrame frame = new GameFrame();
        


    }

}
