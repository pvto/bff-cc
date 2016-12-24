
package bff.syntax;

import bff.$;

public final class sBuilder {

    
    static public Syntax lang(final Object[] lang, final char Sc, final String S)
    {
        Syntax.Holder h = new Syntax.Holder();
        for (int i = 0; i < lang.length; ) { 
            final char c = (char)lang[i++];
            final String y = (String)lang[i++];
            Syntax sy; 
            if (y.startsWith("#"))
                sy = new Syntax.sT(c);
            else
                sy = new Syntax(c);
            sy.s  = y;
            h.addSyntax(sy);
        }
        Syntax start = new Syntax();
        start.x= Sc; 
        start.s= S;
        h.addSyntax(start);
        start.init(h);
        return start;
    }

    static public Syntax vlang(final Object[] lang, final char Sc, final String S)
    {
        Syntax.Holder h = new Syntax.Holder();
        for (int i = 0; i < lang.length; )
        { 
            final char c = (char)lang[i++];
            final String y = (String)lang[i++];
            Syntax s;
            if (y.startsWith("#"))
                s = new Syntax.sT(c);
            else
                s = new Syntax(c);
            s.s = y;
            Object o = lang[i++];
            if (o != null) {
                if (bff.RT.classIs(o, $.class))
                    s.eval = ($)o;
                else if (bff.RT.classIs(o, $[].class)) {
                    s.evalTmp = ($[])o;
                }
                else bff.RT.throwRte("Supplied 3rd term is not a verb nor an array of such - " + o);
            }
            h.addSyntax(s);
        }
        Syntax start = new Syntax(Sc);
        start.s = S;
        h.addSyntax(start);
        start.init(h);
        return start;
    }
}
