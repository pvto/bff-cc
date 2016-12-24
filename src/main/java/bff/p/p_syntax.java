package bff.p;

import bff.io.FeatureInputStream;
import bff.syntax.Syntax;
import bff.syntax.Syntax.Holder;
import java.io.IOException;
import java.io.InputStream;

public class p_syntax implements p_ {

    @Override public p_ addSub$(bff.Symbol[] trigger, p_ subParser) { return this; } // nonsense

    @Override public void parse(InputStream in, bff.Compiler C) throws IOException {
        FeatureInputStream fin = bff.RT.fin(in);
        fin.skipDelims();
        bff.Symbol s;
        Holder defaults = new Holder();
        fin.mark();
        while((s = C.symb(fin.readString())) == C.kw_syntax) {
                fin.skipDelims(); 
                char x = (char)fin.readUtf8();
                if ('o' == x) {
                    bff.RT.throwParsee(fin, "operator syntax [o] can't be redefined");
                }
                int par = fin.readUtf8();
                Syntax parent = null;
                if (!Character.isSpaceChar(par)) {
                   parent = defaults.syntaxes[par];
                   if (parent == null) parent = C.syntaxes.syntaxes[par];
                   if (parent == null)
                       bff.RT.throwParsee(fin, "parent syntax ["+par+"] for ["+x+"] unavailable");
                }
                fin.skipDelims();
                String def = fin.readString();
                Syntax sy = null;
                if (parent != null) {
                         if (bff.RT.classIs(parent, Syntax.sS.class)) {sy = new Syntax.sS(x);}
                    else if (bff.RT.classIs(parent, Syntax.sE.class)) {sy = new Syntax.sE(x);}
                    else if (bff.RT.classIs(parent, Syntax.sN.class)) {sy = new Syntax.sS(x);}
                    else if (bff.RT.classIs(parent, Syntax.sO.class)) {sy = new Syntax.sO(x);}                         
                    if (bff.RT.classIn(parent,
                            Syntax.sS.class, Syntax.sE.class, Syntax.sN.class
                    )) {
                        fin.skipDelims();
                        String parserClass = fin.readString();
                        sy.parser$(bff.RT.requireClass(fin, bff.RT.getNewInstance(fin, parserClass), bff.$.class));
                    }
                } else {
                    sy = new Syntax(x);
                }
                sy.setDirty(true); // mark this for processing later
                sy.s = def;
                C.syntaxes.addSyntax(sy);
                fin.mark();
        }
        fin.rewind();
    }
    
}
