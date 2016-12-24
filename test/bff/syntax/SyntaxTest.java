
package bff.syntax;

import bff.$;
import bff.Scope;
import bff.Verb;
import bff._int;
import static bff.syntax.sBuilder.vlang;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paavo
 */
public class SyntaxTest {

    
    @Test public void testEvalTmp() throws IOException
    {
        $ second = new $.Id(){{name="2nd";} public Object eval(Scope s, Object a, Object b) { return b; } };
        Object[] vlang = {
            'i', "#-?[0-9]+", _int.iparse,
            '+', "#\\+", null,
            '.', "#\\*", null,
            'm', "i<.m>?", new $[]{ _int.imul, null, second},
            'a', "m<+a>?", new $[]{ _int.iadd, null, second}
        };
        Lexer L = new Lexer(vlang(vlang, 'S', "a"));
        Verb res = L.lex(bff.RT.fin("3+1*2*2+1"));
        res.printMatter(System.out);
        res.printVerbMatter(System.out, false);
        res.printVerbMatter(System.out, true);
        assertEquals(8, res.eval(null));
    }
}