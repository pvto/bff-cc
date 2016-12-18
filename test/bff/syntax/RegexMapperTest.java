package bff.syntax;

import static bff.RT.fin;
import bff.io.FeatureInputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RegexMapperTest {
    
    @Test public void testMapSimple() throws IOException {
        RegexMapper<Integer> rl = new RegexMapper.Builder()
                .mapAll(
                        "abc", 1,
                        "def", 2,
                        "aaa", 3
                )
                .build();
        FeatureInputStream in = fin("defabcaaa");
        assertEquals(2, (int)rl.readNext(in).u);
        assertEquals(1, (int)rl.readNext(in).u);
        assertEquals(3, (int)rl.readNext(in).u);
    }
    
    @Test public void testMapWsp() throws IOException {
        RegexMapper<Integer> rl = new RegexMapper.Builder()
                .mapAll(
                        "[ \t\r\n]+", 0,
                        "a+b?", 1,
                        "b+", 2
                )
                .build();
        FeatureInputStream in = fin("  ab bb aaabb");
        assertReadSequence(rl, in, 0,1,0,2,0,1,2,null);
    }
    
    @Test public void testMapSamePrefix() throws IOException {
        RegexMapper<Integer> rl = new RegexMapper.Builder()
                .mapAll(
                        "a+b*", 1,
                        "a+c*", 2
                )
                .build();
        FeatureInputStream in = fin("aacaaabaccab");
        assertReadSequence(rl, in, 2,1,2,1,null);
    }
    
    @Test public void testMapSamePrefix3() throws IOException {
        RegexMapper<Integer> rl = new RegexMapper.Builder()
                .mapAll(
                        "a+b*c", 1,
                        "a+b*d", 2
                )
                .build();
        FeatureInputStream in = fin("aacaadaabcaabd");
        assertReadSequence(rl, in, 1,2,1,2,null);
    }
    
    private <T> void assertReadSequence(RegexMapper<T> rl, FeatureInputStream in, T ... ts) throws IOException {
        for(T t : ts)
            assertEquals(t, (T)rl.readNext(in).u);
    }

    
}
