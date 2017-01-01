package bff.p;

import bff.Compiler;
import bff.Symbol;
import bff.syntax.Syntax;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class p_operators implements p_ {

    @Override public p_ addSub$(Symbol[] trigger, p_ subParser) { return this; } // nonsense

    @Override
    public void parse(InputStream in, Compiler C) throws IOException {
        final bff.io.FeatureInputStream fin = bff.RT.fin(in);
        final Syntax opparser = C.syntaxes.syntaxes['o'];
        final List<bff.$> ops = new ArrayList<>();
        opparser.eval = new bff.$.Id("op") {
            @Override public Object eval(bff.Scope s, Object o, Object p) {
                for(bff.$ op : ops) {
                    Symbol opname = (bff.Symbol)op.eval(s);
                    if (o.equals(opname)) return op.eval(s, p);
                }
                for(bff.$ op : ops) {
                    Symbol opname = (bff.Symbol)op.eval(s);
                    if (opname.repr.equals(o.toString()))
                        return op.eval(s, p);
                }
                bff.RT.throwParsee(fin, "operator [ "+o.toString()+" ] is not defined");
                return null;
            }
            @Override public Object eval(bff.Scope s, Object o, Object p, Object p2) {
                for(bff.$ op : ops) {
                    Symbol opname = (bff.Symbol)op.eval(s);
                    if (o.equals(opname)) return op.eval(s, p, p2);
                }
                for(bff.$ op : ops) {
                    Symbol opname = (bff.Symbol)op.eval(s);
                    if (opname.repr.equals(o.toString()))
                        return op.eval(s, p, p2);
                }
                bff.RT.throwParsee(fin, "operator [ "+o.toString()+" ] is not defined");
                return null;
            }
        };
        
        bff.Symbol s = null;
        while((s = mark(C,fin)) == C.kw_op) {
            Symbol op = C.symb(fin.readString());
            if (!opparser.terminal.matcher(op.repr).matches())
                bff.RT.throwParsee(fin, "illegal operator name: "+op.repr);
            fin.skipDelims();
            int precedence = fin.readInt();
            
        }
        fin.rewind();
    }
    
    private Symbol mark(Compiler C, bff.io.FeatureInputStream fin) throws IOException {
        fin.skipDelims();
        fin.mark();
        return C.symb(fin.readString());
    }
}
