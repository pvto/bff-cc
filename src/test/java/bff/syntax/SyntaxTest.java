
package bff.syntax;

import bff.$;
import bff.Scope;
import bff.Verb;
import bff._int;
import static bff.syntax.sBuilder.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paavo
 */
public class SyntaxTest {

    
    final $ second = new $.Id(){{name="2nd";} 
        public Object eval(Scope s, Object a, Object b) { return b; }
        public Object eval(Scope s, Object a, Object b, Object c) { return b; }
    };
    
    @Test public void testEvalTmp() throws IOException
    {
        Object[] vlang = {
            'i', "#-?[0-9]+", _int.iparse,
            '+', "#\\+", null,
            '.', "#\\*", null,
            'm', "i<.m>?", new $[]{ _int.imul, null, second},
            'a', "m<+a>?", new $[]{ _int.iadd, null, second}
        };
        Lexer L = new Lexer(vlang(vlang, 'S', "a"));
        Verb res = L.lex(bff.RT.fin("3+1*2*2+1"));
//        res.printMatter(System.out);
        res.printVerbMatter(System.out, false);
//        res.printVerbMatter(System.out, true);
        assertEquals(8, res.eval(null));
    }

    public static class TestXmlNode {
        String name;
        List<TestXmlNode> children = new ArrayList<TestXmlNode>();
        public TestXmlNode(String name, TestXmlNode... children) { this.name = name; for(TestXmlNode n : children) this.children.add(n); }
        @Override public String toString() {
            return name + ": " + children;
        }
    }
    
    final $ xmlEval = new $.Id(){{name="xml-tag";}
        public Object eval(Scope s, Object a, Object b) {  return new TestXmlNode(a.toString());  }
        public Object eval(Scope s, Object a, Object b, Object c)
        {
            TestXmlNode n = (TestXmlNode)eval(s, a, c);
            n.children.add((TestXmlNode)b);
            return n;
        }
    };

    final Verb.Transformer xmlValidate = new Verb.Transformer() {
        @Override public void transform(Verb v)
        {
            Verb a = v.m(0).m(1), // x->1->n
                 b = v.m(-1).m(1); // x->2->n
            if (!a.word.equals(b.word))
                bff.RT.throwRte("expecting closing tag <"+a.word+">, but found <"+b.word+">");
        }
    };
    
    @Test public void testEvalStructured() throws IOException
    {

        Object[] vlang = {
            'n', "#[a-zA-Z_][0-9a-zA-Z]*", null, new $.Id(){{name="name";}},
            '(', "#<", null, null,
            ')', "#>", null, null,
            '/', "#/", null, null,
            '[', "(/", null, null,
            '1', "(n)", null, second,
            '2', "[n)", null, second,
            'x', "1x?2", xmlValidate, xmlEval
        };
        Lexer L = new Lexer(vvlang(vlang, 'S', "x")){{whitespace = " \t\r\n";}};
        Verb res = L.lex(bff.RT.fin("<a> <b><c><d><e></e></d></c></b></a>"));
        res.printMatter(System.out);
        res.printVerbMatter(System.out, false);
        System.out.println(res.eval(null));
        assertEquals(35, res.terminalCount());
    }
}