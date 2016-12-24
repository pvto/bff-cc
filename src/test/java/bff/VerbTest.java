package bff;

import bff.syntax.Lexer;
import static bff.syntax.sBuilder.vlang;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class VerbTest {

    @Test public void testVerbs() throws IOException
    {
        $ second = new $.Id(){{name="2nd";} public Object eval(Scope s, Object a, Object b) { return b; } };
        Object[] vlang = {
            'i', "#-?[0-9]+", _int.iparse,
            '+', "#\\+", null,
            'A', "+a", second,
            'a', "iA?", _int.iadd,
        };
        Lexer L = new Lexer(vlang(vlang, 'S', "a"));
        Verb res = L.lex(bff.RT.fin("3+33+0"));
        res.printMatter(System.out);
        assertEquals(5, res.terminalCount());
        assertEquals(36, res.eval(null));
    }
}
