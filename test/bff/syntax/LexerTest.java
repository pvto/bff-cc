package bff.syntax;

import bff.Verb;
import bff.io.FeatureInputStream;
import java.io.IOException;
import org.junit.Test;
import static bff.syntax.sBuilder.lang;
import static org.junit.Assert.*;

public class LexerTest {
    

    private Syntax ablang(final String ss, final String terminals) {
        Syntax.Holder h = new Syntax.Holder();
        for(int i = 0; i < terminals.length(); i++) { 
            final char c = terminals.charAt(i);
            h.addSyntax(new Syntax.sT(){{x= c; s= "#"+c;}});
        }
        Syntax lang = new Syntax.sGroup(){{x= 'S'; s= ss;}};
        h.addSyntax(lang);
        lang.init(h);
        return lang;
    }
    
    
    @Test
    public void testLex() throws IOException {
        Syntax lang = ablang("a*bb*a*","ab");
        Lexer L = new Lexer(lang);
        String g = "aabbba";
        FeatureInputStream fin = bff.RT.fin(g);
        Verb res = L.lex(fin);
//        res.print(System.out);
        assertEquals(4, res.matter.size());
        assertEquals(2, res.matter.get(0)./* * */matter.size() );
        assertEquals("a", res.matter.get(0).matter.get(0).word);
        assertEquals("b", res.matter.get(2).matter.get(0).word);
        assertEquals( 2,  res.matter.get(2).matter.size());
        assertEquals("a", res.matter.get(3).matter.get(0).word);
        assertEquals( 1,  res.matter.get(3).matter.size());
    }

    @Test public void testKleene() throws IOException {
        Lexer L = new Lexer(ablang("<ab*>*","ab"));
        Verb res = L.lex(bff.RT.fin("abbaba"));
        assertEquals("b", res.matter.get(0).matter.get(1).matter.get(1).matter.get(0).word);
        assertEquals(1, res.matter.get(0).matter.get(1).matter.get(1).matter.size());
    }

    @Test public void testRecur() throws IOException {
        Object[] lang = {
            'a', "#a",
            'x', "ax*"
        };
        Lexer L = new Lexer(lang(lang, 'S', "x"));
        Verb res = L.lex(bff.RT.fin("aaa"));
        assertEquals(3, res.terminalCount());
    }


    @Test public void testRecur2() throws IOException {
        Object[] lang = {
            'a', "#a",  'b', "#b",
            'x', "ay*",
            'y', "bx*"
        };
        Lexer L = new Lexer(lang(lang, 'S', "x"));
        Verb res = L.lex(bff.RT.fin("ababa"));
        assertEquals(5, res.terminalCount());
    }

    
    @Test public void testBranchRecur() throws IOException {
        Object[] lang = {
            'a',"#a",  'b',"#b", 'c',"#c",
            'x',"ay?z*",
            'y',"bx?",
            'z',"cx?"
        };
        Lexer L = new Lexer(lang(lang, 'S', "x"));
        Verb res;
        assertEquals(5, (res = L.lex(bff.RT.fin("abccc"))).terminalCount());
        assertEquals(8, (res = L.lex(bff.RT.fin("abcabcac"))).terminalCount());
        assertEquals(6, (res = L.lex(bff.RT.fin("ababac"))).terminalCount());
        assertEquals(5, (res = L.lex(bff.RT.fin("abccc"))).terminalCount());
        assertEquals(12, (res = L.lex(bff.RT.fin("ababcacacaca"))).terminalCount());
        assertEquals(7, (res = L.lex(bff.RT.fin("abacacc"))).terminalCount());
        assertEquals(4, (L.lex(bff.RT.fin("acca"))).terminalCount());
    }
    

    @Test public void testAlts() throws IOException {
        Object[] lang = {
            'a',"#a", 'b',"#b",
            'x', "a|b",
            'y', "x*"
        };
        Lexer L = new Lexer(lang(lang, 'S', "y"));
        Verb res = L.lex(bff.RT.fin("aabba"));
        assertEquals(5, res.terminalCount());
    }

    @Test public void testAlts2() throws IOException {
        Object[] lang = {
            'a',"#a", 'b',"#b", 'c',"#c", 'd',"#d",
            'x', "<a|b|cdd>*",
        };
        Lexer L = new Lexer(lang(lang, 'S', "x"));
        Verb res = L.lex(bff.RT.fin("babcdd"));
        assertEquals(6, res.terminalCount());
        assertEquals(12, L.lex(bff.RT.fin("aaacddcddbbb")).terminalCount());
    }

    @Test public void testStruct1() throws IOException {
        Object[] lang = {
            'a',"#1", 'b',"#0", '(',"#\\(", ')',"#\\)",
            'x', "<a|b>|å",
            'å', "<(x)>|x",
        };
        Lexer L = new Lexer(lang(lang, 'S', "å"));
        Verb res = L.lex(bff.RT.fin("(((1)))"));
        assertEquals(7, res.terminalCount());
        assertEquals(1, L.lex(bff.RT.fin("1")).terminalCount());
    }

    @Test public void testStructuredRecursion() throws IOException {
        Object[] lang = {
            'a',"#1", 'b',"#0",  'o',"#\\*",
            'x', "y|<a|b>",
            'y', "<xox>|x"
        };
        Lexer L = new Lexer(lang(lang, 'S', "y"));
        Verb res = L.lex(bff.RT.fin("1*0*1*1"));
        Lexer.simplifyMatter(res);
        res.printMatter(System.out);
        assertEquals(7, res.terminalCount());
    }

    @Test public void testStatementLike() throws IOException {
        Object[] lang = {
            ';', "#;",
            '(', "#\\(",
            ')', "#\\)",

            'i', "#[1-9][0-9]*", 
            'v', "#[a-zA-Z_][a-zA-Z0-9]*",
            'o', "#\\+",
            '=', "#=",
            
            'e', "<eoe>|i|<v(e)>|v|<(e)>",
            'A', "v=e",
            'p', "e",
            '§', "<<A|p>;>*"
                
        };
        Lexer L = new Lexer(lang(lang, 'S', "§")){{whitespace=" \t\r\n";}};
        Verb res = L.lex(bff.RT.fin("f(x + g(y + 7)); foo = 1; y = 2;"));
        Lexer.simplifyMatter(res);
        res.printMatter(System.out);
        assertEquals(20, res.terminalCount());
    }
    
    
    @Test public void testContention() throws IOException {
        Object[] lang = {
            'a',"#a", 'b',"#b", 'c', "#c",
            'x',"aa",
            'y',"ab",
            'å',"cc",
            'z',"<x|y|å>*"
        };
        Lexer L = new Lexer(lang(lang, 'S', "z"));
        Verb res;
        assertEquals(6, (res = L.lex(bff.RT.fin("ccabaa"))).terminalCount());

    }

}
