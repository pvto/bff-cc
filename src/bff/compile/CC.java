package bff.compile;

import bff.syntax.Syntax;

public class CC {

    public bff.Compiler cc(Syntax root) {
        Syntax.Holder h = new Syntax.Holder();
        root.init(h);
        root.lock();
        bff.Compiler C = new bff.Compiler();
        
        return C;
    }
}
