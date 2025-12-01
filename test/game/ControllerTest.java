package test.game;

import game.Controller;
import grid.GameData;

import static org.junit.jupiter.api.Assertions.assertEquals; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ControllerTest {
    GameData gd;

    @BeforeEach
    public void setup(){
        gd = new GameData();
        Controller.setData(gd);
    }
    
    @Test
    public void testMove(){ //Teszteli a movePlayer, playerIsOnTile függvényeket a CONTROLLERBEN és a GAMEDATA ban a megfelelő függvényeket is
        Controller.movePlayer(4, 7);
        int eredmeny = Controller.playerIsOnTile(4, 7);
        int rossz = Controller.playerIsOnTile(3, 3);
        assertEquals(0, eredmeny);
        assertEquals(-1, rossz);
    }

    @Test
    public void testReset(){ //A reset függvények különböző paraméterű újraindítsának tesztelése
        Controller.movePlayer(4, 7);
        Controller.reset(2);
        int eredmeny = Controller.playerIsOnTile(4, 8);
        assertEquals(0, eredmeny);

        Controller.reset(4);
        int elso = Controller.playerIsOnTile(4, 8);
        int masodik = Controller.playerIsOnTile(4, 0);
        int harmadik = Controller.playerIsOnTile(0, 4);
        int negyedik = Controller.playerIsOnTile(8, 4);
        assertEquals(0, elso);
        assertEquals(1, masodik);
        assertEquals(2, harmadik);
        assertEquals(3, negyedik);
    }
}
