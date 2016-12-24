package bff.syntax;

import bff.syntax.Syntax.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class s_Test {
    
    
    @Test
    public void testNest() {
        Syntax x = null;
        Syntax[] ss = new Syntax[] {
                new Syntax(){{x='a'; s="#a";}},
                new Syntax(){{x='b'; s="#b";}},
                x = new Syntax(){{x='c'; s="b|<a<bb>?|<bbb>|<bbbb>*>*";}}
        };
        Holder h = new Holder();
        for(Syntax s : ss) {
            h.addSyntax(s);
        }
        x.init(h);
        //x.printStructure(System.out);
        assertEquals(1, x.structure.length);
        Syntax any = x.structure[0];
        assertEquals(Syntax.sAny.class, any.getClass());
        assertEquals(2, any.structure.length);
        Syntax rep = any.structure[1];
        assertEquals(Syntax.sRepeat.class, rep.getClass());
        Syntax group = rep.structure[0];
        assertEquals(Syntax.sGroup.class, group.getClass());
        assertEquals(2, group.structure.length);
        Syntax any2 = group.structure[1];
        assertEquals(3, any2.structure.length);
        Syntax rep2 = any2.structure[2];
        assertEquals(Syntax.sRepeat.class, rep2.getClass());
        
    }

/*
    @Test(expected = java.lang.RuntimeException.class)
    public void testCollision() {
        Object[] ss = {
            'a',"#a", 'b', "#b",
            'A', "aa", 'B', "aab",
            'C', "<A*B>*"
        };
        s_ lang = lang(ss, 'S', "C");
        lang.printStructure(System.out);
    }
*/
}
