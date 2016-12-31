
package bff.syntax;

import bff.$;
import bff.Scope;
import bff.Verb;
import bff._int;
import static bff.syntax.sBuilder.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author paavo
 */
public class SyntaxTest {

    
    final $ second = new $.Id("2nd") { 
        public Object eval(Scope s, Object a, Object b) { return b; }
        public Object eval(Scope s, Object a, Object b, Object c) { return b; }
    };
    final $ citems = new $.Id("citems") {
        @Override public Object eval(Scope s, Object a, Object b) { return new Object[]{}; }
        @Override public Object eval(Scope s, Object a, Object b, Object c) { return new Object[]{b}; }
        @Override public Object eval(Scope s, Object a, Object b, Object c, Object d) { return new Object[]{b, c}; }
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
        Map attr = new LinkedHashMap();
        List<TestXmlNode> children = new ArrayList<TestXmlNode>();
        public TestXmlNode(String name, TestXmlNode... children) { this.name = name; for(TestXmlNode n : children) this.children.add(n); }
        @Override public String toString() {
            return name + attr + ": " + children;
        }
    }
    
    final $ xmlEval = new $.Id("xml-tag") {
        public Object eval(Scope s, Object a, Object b) {  
            Object[] na = (Object[])a;
            TestXmlNode ret = new TestXmlNode(na[0].toString());
            if (na.length > 1)
            {
                for(int i = 1; i < na.length; i++) { Object[] attr = (Object[])na[i];
                    if (attr[0] instanceof Object[]) { 
                        for(int j = 0; j < attr.length; j++) {
                            Object[] attr2 = (Object[])attr[j];
                            ret.attr.put(attr2[0], attr2[2]);
                        }
                    }
                    else ret.attr.put(attr[0], attr[2]); }
            }
            return ret;
        }
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
                bff.RT.throwRte("expecting closing tag <"+a.word+">, but found <"+b.word+"> at line " + b.position[0] + ":" + b.position[1]);
        }
    };
    
    @Test public void testEvalStructured() throws IOException
    {

        Object[] vlang = {
            'n', "#[a-zA-Z_][0-9a-zA-Z]*", null, new $.Id("name"),
            '^', "#[^\"]*", null, new $.Id("string"),
            '~', "#[^']*", null, new $.Id("string"),
            '"', "#\"", null, null,
            '\'', "#'", null, null,
            '(', "#<", null, null,
            ')', "#>", null, null,
            '/', "#/", null, null,
            '=', "#=", null, null,
            '-', "'~'", null, second,
            '_', "\"^\"", null, second,
            's', "-|_", null, null,
            '[', "(/", null, null,
            'a', "n=s", null, null,
            '1', "(na*)", null, citems,
            '2', "[n)", null, second,
            'x', "1x?2", xmlValidate, xmlEval
        };
        Lexer L = new Lexer(vvlang(vlang, 'S', "x")){{whitespace = " \t\r\n";}};
        Verb res = L.lex(bff.RT.fin("<a x='yz' y=\"A\"> <b><c><d><e e='eh'></e></d></c></b></a>"));
        //res.printMatter(System.out);
        res.printVerbMatter(System.out, false);
        System.out.println(res.eval(null));
        TestXmlNode n = (TestXmlNode) res.eval(null);
        assertEquals("a", n.name);
        assertEquals(2, n.attr.size());
        assertEquals("e", n.children.get(0).children.get(0).children.get(0).children.get(0).name);
        assertEquals(1, n.children.get(0).children.get(0).children.get(0).children.get(0).attr.size());
        
    }
}