package grid;

/**
 * A generic class representing a pair of two values of the same type.
 * Used to store and retrieve two related values, such as coordinates or direction/distance pairs.
 * 
 * @param <T> the type of both elements in the pair
 * @author banhalmitibor
 * @version 1.0
 */
public class Pair<T> {
    /** The first element of the pair. */
    private final T d1;
    
    /** The second element of the pair. */
    private final T d2;

    /**
     * Constructs a new Pair with the specified values.
     * 
     * @param a the first element
     * @param b the second element
     */
    public Pair(T a, T b){
        d1 = a;
        d2 = b;
    }

    /**
     * Gets the first element of the pair.
     * 
     * @return the first element
     */
    public T getX(){
        return d1;
    }

    /**
     * Gets the second element of the pair.
     * 
     * @return the second element
     */
    public T getY(){
        return d2;
    }
}
