package bff.z;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class AmapTest {
    

    @Test
    public void testAmap() {
        Amap a = new Amap();
        assertEquals(null, a.get("foo"));
        a.put("foo", "a");
        assertEquals("a", a.get("foo"));
        assertEquals(null, a.get("fo"));
        assertEquals(null, a.get("foo-"));
        assertEquals(128, a.root.chid.length);
        assertEquals(128, a.root.chid['f'].chid.length);
        a.put("\u0081x", "b");
        assertEquals(256, a.root.chid.length);
        a.put("\u0101x", "b");
        assertEquals(512, a.root.chid.length);
    }
    
    @Ignore
    @Test
    public void testPerf() {
        Amap a = new Amap();
        HashMap m = new HashMap();
        String K = "foo";
        int N = 100000000;
        a.put(K, K);
        m.put(K, K);
        for(int i = 0; i < 5000000; i++) {
            a.put(i+"", i);
            m.put(i+"", i);
        }
        char[] cc = K.toCharArray();
        long t = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            a.get(cc);
        }//string access: 7863 for 1e9  char acc: 5700
        //with 1e6 items, 1e9 char accesses: 5712
        t = System.currentTimeMillis() - t;
        System.out.println(t + " ("+a.size0+" nodes)");
        t = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            m.get(K);
        }//3171 for 1e9
        //with 1e6 items, 1e9 accesses: 3496
        t = System.currentTimeMillis() - t;
        System.out.println(t);
        
    }
    

    
}
