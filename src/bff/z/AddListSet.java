package bff.z;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class AddListSet<T> implements Set<T> {
    private AddList<T> L = new AddList<T>();
    @Override public int        size()                              { return L.size(); }
    @Override public boolean    isEmpty()                           { return L.isEmpty(); }
    @Override public boolean    contains(Object o)                  { return L.contains(o); }
    @Override public Iterator<T>iterator()                          { return L.iterator(); }
    @Override public Object[]   toArray()                           { return L.toArray(); }
    @Override public <T> T[]    toArray(T[] a)                      { return L.toArray(a); }
    @Override public boolean    add(T e)                            { test(e); return L.add(e); }
    @Override public boolean    remove(Object o)                    { return L.remove(o); }
    @Override public boolean    containsAll(Collection<?> c)        { return L.containsAll(c); }
    @Override public boolean    addAll(Collection<? extends T> c)   { for(T e : c) test(e); return L.addAll(c); }
    @Override public boolean    retainAll(Collection<?> c)          { return L.retainAll(c); }
    @Override public boolean    removeAll(Collection<?> c)          { return L.removeAll(c); }
    @Override public void       clear()                             { L.clear(); }
    private void test(T e) { if (contains(e)) throw new IllegalStateException("item ["+e+"] duplicate"); }
}
