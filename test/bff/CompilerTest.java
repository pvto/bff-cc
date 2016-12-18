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
        $ A = RT.crConstant(3).init$(I.INT, new int[]{1,1});
        $ B = RT.crConstant(8).init$(I.INT, new int[]{1,3});
        $ plus = new _int.$iadd().init$(I._add, new int[]{1,2});
        Object res = plus.eval(new Scope(), A, B);
        assertEquals(11, ((Integer)res).intValue());
    }
    
}
