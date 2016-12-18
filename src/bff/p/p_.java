package bff.p;

import java.io.IOException;
import java.io.InputStream;

/* a parser of a subset of F */
public interface p_ {

    p_ addSub$(bff.Symbol[] trigger, p_ subParser);
    void parse(InputStream in, bff.Compiler C) throws IOException;
    
}
