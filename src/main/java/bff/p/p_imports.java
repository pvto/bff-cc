package bff.p;

import bff.io.FeatureInputStream;
import java.io.IOException;
import java.io.InputStream;

//imports JdiL
public class p_imports implements p_ {
    @Override public p_ addSub$(bff.Symbol[] trigger, p_ subParser) { return this; }  // nonsense
    @Override public void parse(InputStream in, bff.Compiler C) throws IOException {
        FeatureInputStream fin = bff.RT.fin(in);
        fin.skipDelims();
        bff.RT.reqnext_(fin, C.kw_imports, C);
        fin.skipDelims();
        for(;;) {
            char m = fin.readChar();
            if (Character.isSpaceChar(m) || m < '\u0020')
                break;
            if (!bff._._loaded.progress.contains(m))
                bff.RT.throwRte("unknown module: "+m+"("+Integer.toHexString(m)+"x)");
            bff._ module = bff._._loaded.get(m);
            synchronized(C) {
                if (C.imported.contains(m))
                    continue;
                module.imp(C, null);
                C.imported.add(m);
            }
        }
    }
}
