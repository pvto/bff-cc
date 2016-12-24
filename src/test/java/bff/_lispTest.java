
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
        $ A = RT.crConstant(3);
        $ B = RT.crConstant(8);
        $ plus = _int.iadd;
        $ lisp = new _lisp.$lisp();
        Object res = lisp.eval(new Scope(), plus, A, B);
        assertEquals("L(+, 3, 8)", 11, res);
    }
    
}
