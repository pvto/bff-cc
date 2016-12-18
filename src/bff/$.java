package bff;

public interface $ {
    public AST node();
    public int[] extref();
    public $ init$(AST node, int[] extref);
    public Object eval(Scope s);
    public Object eval(Scope s, Object o);
    public Object eval(Scope s, Object o1, Object o2);
    public Object eval(Scope s, Object o1, Object o2, Object o3);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest);
}
