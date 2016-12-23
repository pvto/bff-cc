
package bff.syntax;

import bff.$;

public final class sBuilder {

    
    static public Syntax lang(final Object[] lang, final char Sc, final String S)
    {
        Syntax.Holder h = new Syntax.Holder();
        for (int i = 0; i < lang.length; ) { 
            final char c = (char)lang[i++];
            final String y = (String)lang[i++];
            if (y.startsWith("#"))
                h.addSyntax(new Syntax.sT(){{x= c; s= y;}});
            else
                h.addSyntax(new Syntax(){{x= c; s= y;}});
        }
        Syntax start = new Syntax(){{x= Sc; s= S;}};
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
                s = new Syntax.sT(){{x= c; s= y;}};
            else
                s = new Syntax(){{x= c; s= y;}};
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
        Syntax start = new Syntax(){{x= Sc; s= S;}};
        h.addSyntax(start);
        start.init(h);
        return start;
    }
}
