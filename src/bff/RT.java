package bff;

import bff.io.CachingInputStream;
import bff.io.FeatureInputStream;
import bff.p.ParseEx;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RT {

    static public void loadStaticModules() throws InstantiationException, IllegalAccessException {
        Class[] classes = new Class[] {
            bff._int.class,
            bff._java.class,
            bff._lisp.class,
            bff._map.class,
        };
        for(Class clazz : classes) {
            clazz.newInstance(); }
    }
    static public <T extends _> T findModule(T m) { return (T) _._loaded.get(((_)m)._()); }
    
    
    static public final Charset UTF8;
    static {
        UTF8 = Charset.forName("UTF-8");
    }
    static public final Object[] EMPTY_o = {};
    
    
    static public void      noop() {}
    static public <T> T     firstNotNull(T... tt) {  for(T t :tt) if (t != null) return t;
        return null; }
    static public <T> T     last(List<T> tt) { return tt.get(tt.size() - 1); }
    static public boolean   notNulls(Object... xx) {
        for(Object o : xx) if (o == null) return false;
        return true; }
    static public <T> List<T> newList(T... ts) {
        List<T> ret = new ArrayList<>();
        for(T t : ts) ret.add(t);
        return ret;
    }
    static public <T> Iterable<T> newIterable(final T... ts) {
        Iterator<T> it = new Iterator<T>() {
            int i = 0;
            @Override public boolean hasNext() { return i < ts.length; }
            @Override public T next() { return ts[i++]; }
        };
        return new Iterable<T>() {
            @Override public Iterator<T> iterator() { return it; } };
    }
    static public <T> Iterable<T> newIterable(final List<T> ts) {
        Iterator<T> it = new Iterator<T>() {
            int i = 0;
            @Override public boolean hasNext() { return i < ts.size(); }
            @Override public T next() { return ts.get(i++); }
        };
        return new Iterable<T>() {
            @Override public Iterator<T> iterator() { return it; } };
    }
    
    static public void sneakyThrow(Exception e) { throw new RuntimeException(e.getMessage(), e); }
    static public void throwRte(String message) { throw new RuntimeException(message); }
    static public void throwParsee(FeatureInputStream fin, String message) { throw new ParseEx(fin, message); }
    static public void throwParsee(FeatureInputStream fin, String message, Exception y) { throw new ParseEx(fin, message, y); }
    
    static public FeatureInputStream fin(InputStream in) {
        return (in instanceof FeatureInputStream) ? (FeatureInputStream)in 
                : new FeatureInputStream(in instanceof CachingInputStream ? in : new CachingInputStream(in)) ;
    }
    static public FeatureInputStream fin(String s) {
        return fin(new ByteArrayInputStream(s.getBytes(bff.RT.UTF8)));
    }

    static public FeatureInputStream reqnext_(FeatureInputStream fin, Symbol ss, Compiler C) throws ParseEx {
        try {
            if (ss != C.symb(fin.readString()))
                throwParsee(fin, "required " + ss);
        } catch(IOException y) { 
            throwParsee(fin, "required " + ss + " but encountered exception", y); }
        return fin;
    }

    static public boolean classIs(Object o, Class superclazz) {
        return superclazz.isAssignableFrom(o.getClass());
    }
    static public boolean classIn(Object o, Class... superclazzes) {
        for(Class superclazz: superclazzes) { if (classIs(o, superclazz)) return true; }
        return false;
    }
    static public boolean within(Object x, Object... yy) {
        for(Object y : yy)
            if (x == y) {return true;}
        return false;
    }
    
    static public <T> T getNewInstance(FeatureInputStream fin, String className) {
        try {
            return (T)new _java.$java_new().eval(null, className);
        } catch (Exception e) {
            throwParsee(fin, "creating new "+className+"  failed", e);
        }
        return null;
    }
    
    static public <T> T requireClass(FeatureInputStream fin, Object o, Class<T> superclazz) {
        if (!classIs(o, superclazz))
            throwParsee(fin, "object class "+o.getClass()+"  is not a  "+superclazz);
        return (T)o;
    }
    
    static public $Const crConstant(Object val) { return new $Const(val); }

    static public <T> LinkedList<T> ll() { return new LinkedList<T>(); }

    static public Object eval(Scope s, Object a) { return classIs(a, $.class) ? (($)a).eval(s) : a; }
}
