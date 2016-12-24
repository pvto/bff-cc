
package bff;

import static bff.RT.eval;

public class _lisp implements _ {
    static { _ _ = new _lisp(); _._loaded.put(_._(), _);  }
    public char _() { return 'L'; }
    public _ clone() { return new _lisp(); }

    public AST
            Ln
            ;

    public void imp(Compiler C, AST target) {
        Ln = C.nnFC0("L", new $lisp());
        (target!=null?target:C.AST_L_FUNCTIONNS).appendChid(new AST[] {
           Ln
        });
    }
    
    static public class $lisp extends $A {

        public Object eval(Scope s) {  
            return null;  }
        public Object eval(Scope s, Object o) {
            return (($)o).eval(s);  }
        public Object eval(Scope s, Object o1, Object o2) {
            return(($)o1).eval(s, RT.eval(s, o2)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3) {
            return(($)o1).eval(s, RT.eval(s, o2), o3); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), o4); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), o5); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), RT.eval(s, o5), o6); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), RT.eval(s, o5), RT.eval(s, o7), o7); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), RT.eval(s, o5), RT.eval(s, o6), RT.eval(s, o7), o8); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), RT.eval(s, o5), RT.eval(s, o6), RT.eval(s, o7), RT.eval(s, o8), o9); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), RT.eval(s, o5), RT.eval(s, o6), RT.eval(s, o7), RT.eval(s, o8), RT.eval(s, o9), o10); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest) {
            Object o11 = rest[0];
            Object[] nrest = new Object[rest.length - 1];
            System.arraycopy(rest, 1, nrest, 0, nrest.length);
            return(($)o1).eval(s, RT.eval(s, o2), RT.eval(s, o3), RT.eval(s, o4), RT.eval(s, o5), RT.eval(s, o6), RT.eval(s, o7), RT.eval(s, o8), RT.eval(s, o9), RT.eval(s, o10), RT.eval(s, o11), nrest); }
    }
}
