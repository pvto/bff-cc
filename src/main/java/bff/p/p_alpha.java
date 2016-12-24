package bff.p;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/** A parser for parsing a complete F language. This delegates to p_*.  */
public class p_alpha implements p_ {

    public List subp = new ArrayList();

    @Override public p_ addSub$(bff.Symbol[] trigger, p_ subParser) {
        subp.add(trigger);
        subp.add(subParser);
        return this;
    }

    /** parses prolog (imports/syntax declarations) and constructs the skeletal AST and $ trees */
    @Override public void parse(InputStream in, bff.Compiler C) throws IOException {
        bff.io.FeatureInputStream fin = bff.RT.fin(in);
        new p_imports().parse(fin, C);
        new p_syntax().parse(fin, C);
        new p_operators().parse(fin, C);
    }

}
