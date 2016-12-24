package bff;

import bff.syntax.Lexer;
import bff.syntax.Syntax;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class Verb {
    public int id = -1;
    public Syntax syntax;
    public Verb prev;
    public int childIndex = 0;
    public List<Verb> form = new LinkedList<>();
    public List<Verb> matter = new LinkedList<>();
    public String word;
    public int[] position;
    public Lexer.REC[] rec;
    public long record = 0L;

    public bff.$ eval;
    public boolean NEW_SCOPE = false;

    
    public Verb() {
    }

    public Verb(int id) {
        this.id = id;
    }

    public Verb instancedCopy(boolean form) {
        Verb c = new Verb();
        c.id = id;
        c.syntax = syntax;
        c.prev = prev;
        c.childIndex = childIndex;
        c.eval = eval;
        c.NEW_SCOPE = NEW_SCOPE;
        if (form) {
            c.form.addAll(this.form);
        }
        if (rec != null) {
            c.rec = new Lexer.REC[rec.length];
            for (int i = 0; i < rec.length; i++) {
                if (rec[i] != null) {
                    c.rec[i] = rec[i].copyOf();
                }
            }
        }
        return c;
    }

    @Override
    public String toString() {
        return (word != null ? 
                word + Arrays.toString(position) + (record > 0L ? " R" + record : "") 
                : syntax.toString() + (rec != null ? " R" + Arrays.toString(rec) : "")
                ) +
                (eval != null ? (" -> " + eval)  : "");
    }

    public void print(PrintStream out) { print_(out, this, 0, true, true); }
    public void printForm(PrintStream out) { print_(out, this, 0, true, false); }
    public void printMatter(PrintStream out) { print_(out, this, 0, false, true); }
    public void printVerbForm(PrintStream out, boolean indent) { printVerbs_(out, this, indent?2:-1, true, false); }
    public void printVerbMatter(PrintStream out, boolean indent) { printVerbs_(out, this, indent?2:-1, false, true); }

    private static void print_(PrintStream out, Verb x, int indent, boolean printForm, boolean printMatter)
    {
        out.print(bff.Arf.repeat(' ', indent));
        out.println(x);
        if (printForm) {
            for (Verb c : x.form) {
                if (c.syntax.isDirty()) {
                    print_(out, c, indent + 2, false, false);
                    continue;
                }
                c.syntax.setDirty(true);
                print_(out, c, indent + 2, true, false);
                c.syntax.setDirty(false);
            }
        }
        if (printMatter) {
            for (Verb c : x.matter) {
                print_(out, c, indent + 2, false, true);
            }
        }
    }
    
    private static void printVerbs_(PrintStream out, Verb x, int indent, boolean doForm, boolean doMatter)
    {
        char[] intendent = bff.Arf.repeat(' ', Math.max(indent, 0));
        int childIndent = (indent >= 0 ? indent + 2 : indent);
        if (indent >= 0)
            out.print(intendent);
        out.print(x.eval != null ? x.eval : "");
        out.print("(");
        if (doForm && !x.form.isEmpty()) {
            if (indent >= 0) out.println("");
            for (Verb c : x.form) {
                if (c.syntax.isDirty()) {
                    printVerbs_(out, c, childIndent, false, false);
                    continue;
                }
                c.syntax.setDirty(true);
                printVerbs_(out, c, childIndent, true, false);
                c.syntax.setDirty(false);
            }
            if (indent >= 0) { out.println(intendent + ")");} 
            else { out.print(")"); }
        }
        else if (doMatter && !x.matter.isEmpty()) {
            if (indent >= 0) out.println("");
            for (Verb c : x.matter) {
                printVerbs_(out, c, childIndent, false, true);
            }
            if (indent >= 0) { out.println(intendent + ")");} 
            else { out.print(")"); }
        }
        else {
            if (doForm) {
                out.print(x.syntax);
                if (doMatter)
                    out.print(" <- ");
            }
            if (doMatter && x.word != null)
                out.print(x.word);
            out.print(")");
            if (indent >= 0) out.println("");
        }
    }

    public int terminalCount() {
        return tc_(this);
    }

    private static int tc_(Verb a)
    {
        if (a.word != null) {
            return 1;
        }
        int sum = 0;
        for (Verb c : a.matter) { sum += tc_(c); }
        return sum;
    }
    
    
    
    
    public Object eval(Scope scope)
    {
        if (eval == null) {
            if (matter.size() == 1)
            {
                return meval(scope, 0);
            }
            return null;
        }
        Scope s = scope;
        if (NEW_SCOPE) {
            s = new Scope();
            s.parent = scope;
        }
        switch (matter.size()) {
            case 0: return eval.eval(s, word);
            case 1: return eval.eval(s, meval(s, 0));
            case 2: return eval.eval(s, meval(s, 0), meval(s, 1));
            case 3: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2));
            case 4: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3));
            case 5: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4));
            case 6: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4), meval(s, 5));
            case 7: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4), meval(s, 5), meval(s, 6));
            case 8: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4), meval(s, 5), meval(s, 6), meval(s, 7));
            case 9: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4), meval(s, 5), meval(s, 6), meval(s, 7), meval(s, 8));
            case 10: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4), meval(s, 5), meval(s, 6), meval(s, 7), meval(s, 8), meval(s, 9));
            default: return eval.eval(s, meval(s, 0), meval(s, 1), meval(s, 2), meval(s, 3), meval(s, 4), meval(s, 5), meval(s, 6), meval(s, 7), meval(s, 8), meval(s, 9), getEvaldMatter(s, 10));
        }
    }

    public Object meval(Scope scope, int ind) { return matter.get(ind).eval(scope); }

    public Object[] getEvaldMatter(Scope scope, int offset)
    {
        Object[] res = new Object[matter.size() - offset];
        for (int i = offset; i < matter.size(); i++) {
            res[i - offset] = meval(scope, i);
        }
        return res;
    }

}
