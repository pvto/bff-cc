package bff;

public class $A implements $ {
    public AST node;  public AST node() { return node; }
    public int[] extref;  public int[] extref() { return extref; }
    public $A init$(AST node, int[] extref) { this.node = node;  this.extref = extref;  return this; }
    public Object eval(Scope s) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o) {
        if (o instanceof $) {
            return (($)o).eval(s);
        }
        return o;
    }
    public Object eval(Scope s, Object o1, Object o2) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) { throw new IllegalArgumentException("wrong number of args"); }
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest) { throw new IllegalArgumentException("wrong number of args"); }
}
