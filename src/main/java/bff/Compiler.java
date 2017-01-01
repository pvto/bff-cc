package bff;

import bff.z.Amap;
import bff.z.AddListSet;
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

    
    final public Set imported = new AddListSet();

    // TODO: will this class be used?
}
