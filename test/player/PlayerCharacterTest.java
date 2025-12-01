package test.player;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.PlayerCharecter;

public class PlayerCharacterTest {
    
    PlayerCharecter p;

    @BeforeEach
    public void setup(){
        p = new PlayerCharecter(5, 6);
    }

    @Test
    public void contructorTest(){ //Játékos konstruktor pozíció beállításának tesztje
        int x = p.getX();
        int y = p.getY();
        assertEquals(5, x);
        assertEquals(6, y);
    }

    @Test
    public void testWallPlace(){ //Fal lerakásának tesztje
        boolean e = p.canPlaceWall();
        assertTrue(e);

    }

    @Test
    public void revertWallTest(){ //Lerakott falak számának csökkentésének tesztje
        for(int i = 0; i < 10; i++){
            p.canPlaceWall();
        }
        boolean elfogyott = !p.canPlaceWall();
        assertTrue(elfogyott);
        p.revertWall();
        boolean vanfal = p.canPlaceWall();
        assertTrue(vanfal);
    }

}
