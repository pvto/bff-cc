
package bff;

public class _map implements _ {
    static { _ _ = new _map(); _._loaded.put(_._(), _);  }
    @Override public char _() { return '#'; }
    @Override public _ clone() { return new _map(); }
    
    public void imp(Compiler C, AST target) {
        (target!=null?target:C.AST_L_FUNCTIONNS).appendChid(new AST[] {
            C.nnFC0("map", new $map())
        });
    }

    static public class $map extends $A {
        
        public Object eval(Scope s, Object o1) {
            return map(s, Arf.objs(o1)); }
        public Object eval(Scope s, Object o1, Object o2) {
            return map(s, Arf.objs(o1, o2)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3) {
            return map(s, Arf.objs(o1, o2, o3)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4) {
            return map(s, Arf.objs(o1, o2, o3, o4)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5) {
            return map(s, Arf.objs(o1, o2, o3, o4, o5)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
            return map(s, Arf.objs(o1, o2, o3, o4, o5, o6)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
            return map(s, Arf.objs(o1, o2, o3, o4, o5, o6, o7)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
            return map(s, Arf.objs(o1, o2, o3, o4, o5, o6, o7, o8)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
            return map(s, Arf.objs(o1, o2, o3, o4, o5, o6, o7, o8, o9)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
            return map(s, Arf.objs(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10)); }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest) {
            return map(s, Arf.concato(Arf.objs(o1, o2, o3, o4, o5, o6, o7, o8, o9, o10), rest)); }
        
        
        public Object map(Scope s, Object... oo) {
            Object[] ret = Arf.Nobjs(oo.length);
            for(int i = 0; i < oo.length; i++) {
                ret[i] = super.eval(s, oo[i]);
            }
            return ret;
        }
    }
    

}
