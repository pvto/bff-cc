
package bff;

public class Symbol {
    public int id;
    public String repr;
    public Symbol(int id, String repr) {
        this.id = id;
        this.repr = repr.intern();  }
    @Override
    public boolean equals(Object b) {
        if (!(b instanceof Symbol)) return false;
        Symbol B = (Symbol)b;
        return B.id == id;
    }
    @Override
    public int hashCode() { return id; }
}