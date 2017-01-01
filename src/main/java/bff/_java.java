package bff;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class _java implements _ {
    static { _ _ = new _java(); _._loaded.put(_._(), _);  }
    @Override public char _() { return 'J'; }
    @Override public _ clone() { return new _java(); }
    
    
    static public class $java_new implements $ {

        @Override public Object eval(Scope s) { throw new UnsupportedOperationException("Can't j-evaluate empty"); }
        public Object eval(Scope s, Object o) {
            return getInstance(getClazz((String)o)); }
        public Object eval(Scope s, Object o, Object o2) {
            return getInstance(getClazz((String)o), Arf.objs(o2)); }
        public Object eval(Scope s, Object o, Object o2, Object o3) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4, o5)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5, Object o6) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4, o5, o6)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4, o5, o6, o7)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4, o5, o6, o7, o8)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4, o5, o6, o7, o8, o9)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
            return getInstance(getClazz((String)o), Arf.objs(o2, o3, o4, o5, o6, o7, o8, o9, o10)); }
        public Object eval(Scope s, Object o, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object... rest) {
            return getInstance(getClazz((String)o), Arf.concato(Arf.objs(o2, o3, o4, o5, o6, o7, o8, o9, o10), rest)); }
    }
    
    static public class $java_call implements $ {

        @Override public Object eval(Scope s) { throw new UnsupportedOperationException("Can't j-call unprovided object; neither was method to call specified"); }
        @Override public Object eval(Scope s, Object o) { throw new UnsupportedOperationException("Can't j-call instance object; method not specified"); }
        public Object eval(Scope s, Object inst, Object m) {
            return javaCall(getMethod(inst,m, RT.EMPTY_o), inst, RT.EMPTY_o); }
        public Object eval(Scope s, Object inst, Object m, Object p1) {
            Object[] args = Arf.objs(p1);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2) {
            Object[] args = Arf.objs(p1, p2);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3) {
            Object[] args = Arf.objs(p1, p2, p3);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3, Object p4) {
            Object[] args = Arf.objs(p1, p2, p3, p4);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3, Object p4, Object p5) {
            Object[] args = Arf.objs(p1, p2, p3, p4, p5);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
            Object[] args = Arf.objs(p1, p2, p3, p4, p5, p6);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
            Object[] args = Arf.objs(p1, p2, p3, p4, p5, p6, p7);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
            Object[] args = Arf.objs(p1, p2, p3, p4, p5, p6, p7, p8);
            return javaCall(getMethod(inst,m,args), inst, args); }
        public Object eval(Scope s, Object inst, Object m, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object... p9) {
            Object[] args = Arf.concato(Arf.objs(p1, p2, p3, p4, p5, p6, p7, p8), p9);
            return javaCall(getMethod(inst,m,args), inst, args); }

        
        Method getMethod(Object inst, Object m, Object[] args) {
            return getClassMethod(getInstanceClass(inst), (String)m, getClasses(args));
        }
    }

    
    
    static public Class getClazz(String fullclass) {
        try {
            return Class.forName(fullclass);
        } catch(Exception e) {
            RT.sneakyThrow(e);
        }
        return null;
    }
    static public Class getInstanceClass(Object inst) {
        try {
            return inst.getClass();
        } catch(Exception e) {
            RT.sneakyThrow(e);
        }
        return null;
    }
    static public Object getInstance(Class clazz) {
        try {
            return clazz.newInstance();
        } catch(Exception e) {
            RT.sneakyThrow(e);
        }
        return null;
    }
    static public Object getInstance(Class clazz, Object... args) {
        try {
            Class[] clazzes = getClasses(args);
            Constructor c = clazz.getConstructor(clazzes);
            return c.newInstance(args);
        } catch(Exception e) {
            RT.sneakyThrow(e);
        }
        return null;
    }
    static public Class[] getClasses(Object... args) {
        Class[] clazzes = new Class[args.length];
        for(int i = clazzes.length - 1; i >= 0; i--) {
            clazzes[i] = args[i].getClass();
        }
        return clazzes;
    }
    
    static public Method getClassMethod(Class clazz, String method, Class... ptypes) {
        try {
            return clazz.getMethod(method, ptypes);
        } catch (Exception e) {
            RT.sneakyThrow(e);
        }
        return null;
    }
    
    static public Object javaCall(Method method, Object inst, Object... args) {
        try {
            return method.invoke(inst, args);
        } catch (Exception e) {
            RT.sneakyThrow(e);
        }
        return null;
    }

}
