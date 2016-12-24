package bff.syntax;

import bff.io.FeatureInputStream;
import bff.io.StreamRegex;
import java.io.IOException;
import java.util.LinkedList;

public final class FinTokenizer {
    
    public final FeatureInputStream fin;
    public FinTokenizer(FeatureInputStream fin) { this.fin = fin; }
    
    static public class Token {
        final public String word;
        final public int[] pos;
        final public int id;
        public Token(FinTokenizer ft, String word, int[] pos) { this.word = word;  this.pos = pos;  this.id = ft.ids++; }
    }
    private int ids = 1;
    
    public final LinkedList<Token> tokenized = new LinkedList<>();

    
    public Token nextWord(StreamRegex reg) throws IOException {
        int[] pos = fin.snapPos();
        String word = reg.readItem(fin);
        if (word == null || word.length() == 0) {return null;}
        Token t;
        tokenized.add(t = new Token(this, word, pos));
        return t;
    }
    
    public void forgetLast() { tokenized.removeLast(); }
    
    public void forgetBackTo(Token t) {
        while(!tokenized.isEmpty()) {
            Token x = tokenized.getLast();
            if (x.id < t.id) {break;}
            tokenized.removeLast();
        }
    }
}
