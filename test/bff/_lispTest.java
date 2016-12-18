
package bff;

import org.junit.Test;
import static org.junit.Assert.*;

public class _lispTest {

    @Test
    public void testLisp() {
        Compiler C = new Compiler();
        AST root = C.rootAst;
        _lisp L = new _lisp();
        L.imp(C, root);
        _int I = new _int();
        I.imp(C, root);
        $ A = RT.crConstant(3).init$(I.INT, Arf.ints(0,5));
        $ B = RT.crConstant(8).init$(I.INT, Arf.ints(0,8));
        $ plus = new _int.$iadd().init$(I._add, Arf.ints(0,2));
        $ lisp = new _lisp.$lisp().init$(L.Ln, Arf.ints(0,0));
        Object res = lisp.eval(new Scope(), plus, A, B);
        assertEquals("L(+, 3, 8)", 11, res);
    }
    
}
