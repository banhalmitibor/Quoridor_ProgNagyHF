package test.grid;

import static org.junit.jupiter.api.Assertions.assertEquals; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grid.Pair;

public class PairTest {
    

    @Test
    public void pairTest(){
        Pair<Integer> p = new Pair<>(12,13);
        int x = p.getX();
        int y = p.getY();
        assertEquals(12, x);
        assertEquals(13, y);

    }
    
}
