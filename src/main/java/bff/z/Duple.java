
package bff.z;

public class Duple<T,U> {

    public T t;
    public U u;
    
    public Duple(T t, U u) { this.t = t; this.u = u; }
    
    @Override public String toString() { return String.format("<%s,%s>", t, u); }
}
