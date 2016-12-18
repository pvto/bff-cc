package bff.p;

import bff.Arf;
import bff.Compiler;
import bff.RT;
import bff._;
import bff._int;
import bff._lisp;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

public class p_Test {
    
    @Test
    public void p_importsTest() throws IOException, InstantiationException, IllegalAccessException {
        RT.loadStaticModules();
        p_imports i = new p_imports();
        assertTrue(_._loaded.size() > 0);
        Compiler C = new Compiler();
        String s = "imports iJL\r\nfn x (y ()) ";
        InputStream in = new ByteArrayInputStream(s.getBytes(RT.UTF8));
        i.parse(in, C);
        assertTrue(null == Arf.find(C.AST_L_FUNCTIONNS.chid, new _lisp().Ln));
        assertTrue(null != Arf.find(C.AST_L_FUNCTIONS.chid, RT.findModule(new _int())._add));
        assertTrue(null != Arf.find(C.AST_L_FUNCTIONNS.chid, RT.findModule(new _lisp()).Ln));
        
    }
    
}
