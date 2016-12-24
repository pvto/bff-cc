package bff.z;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/** a map containing linked lists */
public class ListMap<K,V> extends HashMap<K,List<V>> {
    
    private final List<V> emptyList = Collections.emptyList();
    
    public void add(K key, V value) {
        List<V> ss = get(key);
        if (ss == null) {
            put(key, ss = new LinkedList<>());
        }
        ss.add(value);
    }
    
    @Override public List<V> get(Object key) { return bff.RT.firstNotNull(super.get(key), emptyList); }
    
}
