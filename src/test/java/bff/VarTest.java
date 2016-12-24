package bff;

import org.junit.Test;
import static org.junit.Assert.*;

public class VarTest {
    
    @Test
    public void testVar() {
        $Var v = new $Var();
        Scope parent = new Scope();
        Scope child = new Scope().parent$(parent);
        Scope gchild = new Scope().parent$(child);
        Symbol s = new Symbol(1, "x");
        v.eval(parent, s, 1);
        v.eval(child, s, 10);   // set to 10
        assertEquals("scope(par) never modified", 1, parent.vars.size());
        assertEquals("scope(chi) never modified", 1, child.vars.size());
        assertEquals(10, v.eval(gchild, s));
        assertEquals(10, v.eval(child, s));
        assertEquals(1, v.eval(parent, s));
        assertEquals(null, v.eval(child, new Symbol(2, "y")));
    }
}
