
package bff;

import org.junit.Test;
import static org.junit.Assert.*;

public class _javaTest {

    @Test
    public void testJava()
    {
        _java JAVA = new _java();
        $ c = new _java.$java_new();
        $ i = new _java.$java_call();
        Object inst = c.eval(new Scope(), "java.lang.String", "abc");
        assertEquals("abc", inst);
        Object m = i.eval(new Scope(), inst, "replaceAll", "b", "/");
        assertEquals("a/c", m);
    }
    
}
