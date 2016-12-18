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
            new bff.syntax.Syntax.s_Ni(),         new p_int()
        };
        for(int i = 0; i < ss.length; i+=2)
            {C.syntaxes.addSyntax( ((bff.syntax.Syntax)ss[i++]) .parser$(($)ss[i++]) );}
    }

    static public class p_int extends bff.$A {  @Override public Object eval(Scope sc, Object str, Object compiler) {
            return Integer.parseInt((String)str, ((String)str).startsWith("0x")?16:10); }}

    
    static public class $iadd extends $A {
        public Object eval(Scope s, Object a, Object b)
        {
            return (Integer)eval(s, a) + (Integer)eval(s, b);
        }
    }
    public static class $isub extends $A {
        public Object eval(Scope s, Object a, Object b)
        {
            return (Integer)eval(s, a) - (Integer)eval(s, b);
        }
    }
}
