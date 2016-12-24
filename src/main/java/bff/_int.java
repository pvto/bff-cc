package bff;

public class _int implements _ {
    static { _ _ = new _int(); _._loaded.put(_._(), _);  }
    public char _() { return 'i'; }
    public _ clone() { return new _int(); }

    
    public AST
            INT,
            _add,
            _sub
            ;
    @Override
    public void imp(Compiler C, AST target) {
        // TODO: remove method from _ ?
    }

    
    static public final $ iparse = new $.Id("(int)")
        {public Object eval(Scope s, Object a) { return Integer.parseInt((String)a, ((String)a).startsWith("0x")?16:10); }};
    
    static public final $ iadd = new $.Id("+")
        {public Object eval(Scope s, Object a, Object b) { return (Integer)RT.eval(s, a) + (Integer)RT.eval(s, b); }};
    static public final $ isub = new $.Id("-")
        {public Object eval(Scope s, Object a, Object b) { return (Integer)RT.eval(s, a) - (Integer)RT.eval(s, b); }};
    static public final $ imul = new $.Id("*")
        {public Object eval(Scope s, Object a, Object b) { return (Integer)RT.eval(s, a) * (Integer)RT.eval(s, b); }};
    static public final $ idiv = new $.Id("/")
        {public Object eval(Scope s, Object a, Object b) { return (Integer)RT.eval(s, a) / (Integer)RT.eval(s, b); }};
    static public final $ imod = new $.Id("%")
        {public Object eval(Scope s, Object a, Object b) { return (Integer)RT.eval(s, a) % (Integer)RT.eval(s, b); }};

}
