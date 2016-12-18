package bff.compile;

import bff.Compiler;
import bff.Scope;
import bff.syntax.Syntax;
import bff.syntax.Syntax.Holder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.Test;

public class CCTest {
    
    Compiler ab() {
        bff.$ str = new bff.$A() {
            @Override public Object eval(Scope sc, Object str, Object compiler) {  
                return str.toString();
            }
        };
        Syntax a = new Syntax.sT(){{x= 'a'; s= "#a";}}.parser$(str);
        Syntax b = new Syntax.sT(){{x= 'b'; s= "#b";}}.parser$(str);
        Syntax lang = new Syntax(){{x= 'S'; s= "a*bb*a*";}};
        Holder h = new Holder();
        for(Syntax x : new Syntax[]{a,b,lang})
            h.addSyntax(x);
        h.initAll();
        CC cc = new CC();
        Compiler C = cc.cc(lang);
        return C;
    }

    @Test
    public void abSuccess() {
        Compiler C = ab();
        C.accept(in("aaaba"));
//        C.accept(in("aaaaab"));
    }
    
    InputStream in(String s) {
        return new ByteArrayInputStream(s.getBytes(bff.RT.UTF8));
    }
}
