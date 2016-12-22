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
        INT = C.nnFC0("int");
        _add = C.nnFC3("+");
        _sub = C.nnFC3("-");
        (target!=null?target:C.AST_L_FUNCTIONS).appendChid(new AST[] {
           _add, _sub 
        });
        Object[] ss = new Object[] {
            new bff.syntax.Syntax.s_Ni(),         iparse,
        };
        for(int i = 0; i < ss.length; i+=2)
            {C.syntaxes.addSyntax( ((bff.syntax.Syntax)ss[i++]) .parser$(($)ss[i++]) );}
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
