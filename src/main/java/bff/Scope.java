package bff;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    public Scope parent;  public Scope parent$(Scope par) { this.parent = par;  return this; }
    public Scope root;  public Scope root$(Scope root) { this.root = root;  return this; }
    public Map vars = new HashMap();
    public Object resolve(Symbol s) {
        Object a = vars.get(s);
        if (a != null) return a;
        if (parent == null) return null;
        return parent.resolve(s);
    }
    public Object set(Symbol s, Object val) {
        vars.put(s, val);
        return val;
    }
}