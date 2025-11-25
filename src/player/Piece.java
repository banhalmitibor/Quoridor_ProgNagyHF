package player;

public abstract class Piece {
    protected int posx;
    protected int posy;
    protected int wallnum;

    public Piece(int x, int y){
        posx = x;
        posy = y;
        wallnum = 0;
    }

    public int getX() {return posx;}

    public int getY() {return posy;}

    public void setPos(int x, int y){
        posx = x;
        posy = y;
    }

    public boolean canPlaceWall(){
        return wallnum++ < 10;
    }

    public void revertWall(){
        wallnum--;
    }

    public abstract void makeMove();

}
