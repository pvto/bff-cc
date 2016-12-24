package bff.z;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddMap<K,V> extends HashMap<K,V> {
    
    public Set<K> progress = new AddListSet<K>();   // client should never modify; care to impl to prohibit?
    @Override public V      put(K key, V value)                     { progress.add(key); return super.put(key, value); }
    @Override public void   putAll(Map<? extends K, ? extends V> m) { progress.addAll(m.keySet()); super.putAll(m); }
}
