package bff;

import bff.io.FeatureInputStream;
import bff.z.Amap;
import bff.z.AddListSet;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Compiler {
    
    public final bff.syntax.Syntax.Holder syntaxes = new bff.syntax.Syntax.Holder();
    
    public final Amap symbm = new Amap();
    public final Symbol[] symb = new Symbol[16384];
    public Symbol   symb(int id) { return symb[id]; }    
    private Symbol  symb(int id, String repr) {
        if (id >= symb.length)
            throw new IllegalStateException("oh no! f.Symbol - space exhausted? - id:" + id);
        if (symb[id] != null || symbm.get(repr) != null)
            throw new java.lang.IllegalStateException("attempted redefinition of f.Symbol "+repr);
        Symbol s = new Symbol(id, repr);  
        symbm.put(repr, s);
        symb[id] = s;
        return s;  }
    public AtomicInteger
            FC = new AtomicInteger(1)
            ;
    public Symbol   symb(String repr) {
        Symbol s = (Symbol)symbm.get(repr);
        return (s != null)? s : symb(FC.getAndIncrement(), repr);  }

    final public Symbol 
            kw_imports= symb("imports"),
            kw_syntax= symb("syntax"),
            kw_semicolon= symb(";"),
            kw_op= symb("op"),
            kw_null= symb("null"),
            kw_fn= symb("fn")
            ;
    final public Symbol
            ROOT = symb("<ROOT>"),
            FUNCTIONS = symb("<FUNCTIONS>"),
            FUNCTIONNS = symb("<FUNCTIONNS>"),
            FUNCTIONN = symb("fnn"),
            CONSTANTS = symb("const"),
            LANG = symb("lang")
            ;

    public AST nn(Symbol symb, AST[] chid, $ fn){ return new AST(symb, chid).fn$(fn); }
    public AST nn(Symbol symb, AST[] chid)      { return nn(symb, chid, null); }
    public AST nn(String repr, AST[] chid, $ fn){ return nn(symb(repr), chid, fn); }
    public AST nnFC0(String repr)       { return nn(repr, new AST[] {}, null); }
    public AST nnFC0(String repr, $ fn) { return nn(repr, new AST[] {}, fn); }
    public AST nnFC2(String repr)       { return nn(repr, new AST[] { 
            nn(kw_fn, null), nn(kw_fn, null)  }, null); }
    public AST nnFC3(String repr)       { return nn(repr, new AST[] { 
            nn(kw_fn, null), nn(kw_fn, null), nn(kw_fn, null) }, null); }
        
    final public AST rootAst =
        nn(ROOT, new AST[]{
            nn(kw_null, new AST[]{}),
            nn(FUNCTIONS, new AST[] {}),
            nn(FUNCTIONNS, new AST[] {}),
            nn(CONSTANTS, new AST[] {}),
            nn(LANG, new AST[] {})
        });
    final public AST 
            AST_L_FUNCTIONS = rootAst.chid[1],
            AST_L_FUNCTIONNS = rootAst.chid[2],
            AST_L_CONSTANTS = rootAst.chid[3],
            AST_L_LANG = rootAst.chid[4]
            ;

    
    final public Set imported = new AddListSet();

    public Object accept(InputStream in) {
        FeatureInputStream fin = RT.fin(in);
        return AST_L_LANG.fn.eval(new Scope(), fin);
    }
}
