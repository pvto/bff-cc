package bff;

import org.junit.Test;
import static org.junit.Assert.*;

public class CompilerTest {
    

    @Test
    public void testEval() {
        Compiler C = new Compiler();
        AST root = C.rootAst;
        _int I = new _int();
        I.imp(C, root);
        $ A = RT.crConstant(3);
        $ B = RT.crConstant(8);
        $ plus = _int.iadd;
        Object res = plus.eval(new Scope(), A, B);
        assertEquals(11, ((Integer)res).intValue());
    }
    
}
