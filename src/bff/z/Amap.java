
package bff.z;

import static bff.Futil.next2Power;
import static bff.Arf.copyOfsize;

/** A string trie */
public class Amap {
    
    public static class Anode {
        public Anode[] chid; // = new Anode[64];
        public Object a;
        public void expand(int chidi, Amap m)
        {
            if (chid == null) {
                chid = new Anode[next2Power(chidi)];
                m.size0 += chid.length;
            }
            else if (chid.length <= chidi) {
                int szold = chid.length;
                chid = copyOfsize(chid, next2Power(chidi));
                m.size0 += -szold + chid.length;
            }
        }
    }
    public Anode root = new Anode();
    public int size0 = 0;
    
    public Object get(String key) 
    {
        return get(key.toCharArray());
    }
    public Object get(char[] cc)
    {
        Anode rf = root;
        for(int i = 0; i < cc.length; i++)
        {
            if (rf.chid == null || cc[i] >= rf.chid.length)
            {
                return null;
            }
            rf = rf.chid[cc[i]];
            if (rf == null)
            {
                return null;
            }
            if (i == cc.length - 1) 
            {
                return rf.a;
            }
        }
        return rf.a;
    }
    
    
    public Object put(String key, Object val)
    {
        return put(key.toCharArray(), val);
    }
    public Object put(char[] cc, Object val)
    {
        Anode rf = root;
        for(int i = 0; i < cc.length; i++)
        {
            rf.expand(cc[i], this);
            Anode tmp = rf.chid[cc[i]];
            if (tmp == null)
            {
                tmp = new Anode();
                rf.chid[cc[i]] = tmp;
            }
            rf = tmp;
            if (i == cc.length - 1) {
                Object b = rf.a;
                rf.a = val;
                return b;
            }
        }
        return null;
    }
}
