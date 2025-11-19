package grid;

public class Pair<T> {
    private T d1;
    private T d2;

    public Pair(T a, T b){
        d1 = a;
        d2 = b;
    }

    public T getX(){
        return d1;
    }

    public T getY(){
        return d2;
    }
}
