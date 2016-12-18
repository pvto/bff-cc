package bff;

import bff.syntax.Syntax;
import bff.z.Amap;
import bff.z.Amap.Anode;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Arf {

    static public Syntax[]      concat(Syntax[] A, Syntax... B) {
        Syntax[] C = Arrays.copyOf(A, A.length+B.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }
    static public Syntax[]      exclude(Syntax[] q, int excl, int end) {
        Syntax[] r = new Syntax[q.length - 1];
        for(int i = 0, k = 0; i < q.length; i++)
            if (k < excl || k > end) {
                r[k++] = q[i];
            }
        return r;
    }
    
    static public AST[]     concat(AST[] A, AST[] B)
    {
        AST[] C = Arrays.copyOf(A, A.length+B.length);
        System.arraycopy(B, 0, C, A.length, B.length);
        return C;
    }
    
    
    
    static public Anode[]   copyOfsize(Anode[] A, int size)
    {
        Amap.Anode[] tmp = new Amap.Anode[size];
        System.arraycopy(A, 0, tmp, 0, A.length);
        return tmp;
    }
    
    
    
    static public Object[]  objs(Object... o) { return o; }
    static public Object[]  Nobjs(int i) { return new Object[i]; }
    static public Object[]  concato(Object[] a, Object[] b) {
        Object[] c = new Object[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c; }

    
    static public int[]     ints(int... I) { return I; }

    static public AST       find(AST[] L, AST inst) {
        for(AST y : L) if (y == inst || y.equals(inst)) return y;
        return null;
    }

    static public ByteArrayOutputStream copyOfRange(ByteArrayOutputStream cache, int start, int end) {
        byte[] chunk = Arrays.copyOfRange(cache.toByteArray(), start, end);
        ByteArrayOutputStream ret = new ByteArrayOutputStream();
        try { ret.write(chunk); } catch(Exception foo) { bff.RT.noop(); }
        return ret;
    }

    static public int       count(String s, char c) {
        int count = 0;
        for(int i = 0; i < s.length(); i++) {if(c == s.charAt(i)) {count++;}}
        return count; }

    static public void      insertAtNextNull(Syntax[] structure, Syntax x) {
        for(int i = 0; i < structure.length; i++) {
            if (structure[i] == null) {
                structure[i] = x;  return;
            }
        }
        throw new IndexOutOfBoundsException("array is full, can't insert " + x);
    }

    static public boolean   within(char y, char... cc) {
        for(char c : cc) if (y == c) {return true;}
        return false; }

    static public char[]    repeat(char c, int n) {
        char[] ret = new char[n];
        for(int i = 0; i < n; i++)
             ret[i] = c;
        return ret;
    }

    static public int[]     copyOf(int[] A) { return Arrays.copyOf(A, A.length); }


}
