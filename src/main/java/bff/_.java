
package bff;

import bff.z.AddMap;

public interface _ {

    public char _();
    public _ clone();    
    
    static public final class def {
        static public boolean _unsupported(char key) {
            return Character.isSpaceChar(key) || key < '\040';
        }
    }
    // if you implement a module, remember to register it in here
    static public final AddMap<Character, _> _loaded = new AddMap<Character, _>() {
        @Override
        public _ put(Character key, _ value) {
            if (def._unsupported(key))
                RT.throwRte("unsupported module key char "+key+"("+Integer.toHexString(key));
            return super.put(key, value);
        }
    };
    
}
