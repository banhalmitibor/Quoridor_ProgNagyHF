package player;

public abstract class Piece {
    protected  int posx;
    protected  int posy;

    public Piece(int x, int y){
        posx = x;
        posy = y;
    }

    public int getX() {return posx;}

    public int getY() {return posy;}

    public void setPos(int x, int y){
        posx = x;
        posy = y;
    }

    public abstract void makeMove();

}
