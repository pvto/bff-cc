package bff;

public interface $ {

    public Object eval(Scope s);
    public Object eval(Scope s, Object o);
    public Object eval(Scope s, Object o1, Object o2);
    public Object eval(Scope s, Object o1, Object o2, Object o3);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10);
    public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest);
    
    
    public static class Id implements $ {
        
        public String name;
        
        public Id(){}
        public Id(String name){ this.name = name; }
        
        public Object eval(Scope s) { return null; }
        public Object eval(Scope s, Object o1) { return o1; }
        public Object eval(Scope s, Object o1, Object o2) { return new Object[]{o1,o2}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3) { return new Object[]{o1,o2,o3}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4) { return new Object[]{o1,o2,o3,o4}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5) { return new Object[]{o1,o2,o3,o4,o5}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) { return new Object[]{o1,o2,o3,o4,o5,o6}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) { return new Object[]{o1,o2,o3,o4,o5,o6,o7}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) { return new Object[]{o1,o2,o3,o4,o5,o6,o7,o8}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) { return new Object[]{o1,o2,o3,o4,o5,o6,o7,o8,o9}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) { return new Object[]{o1,o2,o3,o4,o5,o6,o7,o8,o9,o10}; }
        public Object eval(Scope s, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest) { return new Object[]{o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,rest}; }

        @Override public String toString() { return name != null ? name : super.toString(); }
    }
}
