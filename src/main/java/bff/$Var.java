package bff;

public class $Var extends $A {
    
    public Object eval(Scope s, Object name) {
        return s.resolve((Symbol)name);
    }
    
    public Object eval(Scope s, Object name, Object val) {
        return s.set((Symbol)name, val);
    }

}
