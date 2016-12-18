
package bff;

public final class Futil {
    
    private Futil() {}
    
    
    public static int next2Power(int i) {
        int x = 1;
        while(x <= i) {
            x <<= 1;
        }
        return x;
    }
}
