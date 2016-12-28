package bff.syntax;

import bff.Verb;
import bff.io.FeatureInputStream;
import bff.io.StreamRegex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public int MAX_RECURSION = 8;
    public String whitespace;

    private void initWhitespace()
    {
        if (whitespace != null) {
            whitespaceReg = new StreamRegex("["+whitespace+"]*");
        } else {
            whitespaceReg = null;
        }
    }
    
    
    

    /** a record of recursion */
    static public class REC {
        public long RECCNT = 0L;
        public final long RECMASK, RECID;
        public REC(long recid) {
            RECID = 1 << (recid * 16);
            RECMASK = 0xFFFFFFFFFFFFFFFFL ^ (0xFFFFL << (recid * 16L));
        }
        public long mask(long REC) { return (REC & RECMASK) + (RECID * RECCNT); }
        public REC copyOf() { REC r = new REC(RECID); r.RECCNT = RECCNT; return r; }
        public String toString() { return RECID+":"+RECCNT; }
    }
    
    private volatile int ids = 1;
    final public Verb proto;
    
    
    public Lexer(Syntax root) {
        init(root, this.proto = new Verb(ids++));
    }

    private void init(Syntax S, Verb L) {
        L.syntax = S;
        if (S.structure != null) {
            for(Syntax c : S.structure) {
                if (c.isDirty()) {
                    boolean recur = false;
                    Verb par = L;
                    while(par != null) {
                        if (par.syntax == c) {
                            L.form.add(par);
                            recur = true; 
                            break;
                        }
                        par = par.prev;
                    }
                    if (recur)
                        continue;
                }
                Verb m = new Verb(ids++);
                m.syntax = c;
                m.prev = L;
                m.childIndex = L.form.size();
                m.postBuildMatter = c.postBuildMatter;
                m.eval = c.eval;
                L.form.add(m);
                c.setDirty(true);
                init(c, m);
                c.setDirty(false);
            }
        }
    }

    public int dirtyMax = 0;
    private StreamRegex whitespaceReg;
    
    public Verb lex(FeatureInputStream fin) throws IOException {
        initWhitespace();
        Verb res = deepCopy(proto);
        dirtyMax = 0;
        lexTree(res, fin, 0L, new ArrayList<WordRec>(), new int[]{0, 0});
//        simplifyMatter(res);
        return res;
    }
    static final private StreamRegex read16 = new StreamRegex("[\t\r\n\\u0020-\\uFFFE]{16}");
    
    private static class WordRec { String word;  int[] streamPos;  long record; }

    
    private boolean lexTree(Verb node, FeatureInputStream fin, long REC,
            List<WordRec> wordStack, int[] stackpos) throws IOException
    {
        if (node.syntax.dirtier > MAX_RECURSION) { return false; }
        node.syntax.dirtier++;
        dirtyMax = Math.max(dirtyMax, node.syntax.dirtier);
        
        boolean ok = lexTerminal(node, fin, REC, wordStack, stackpos)
            || lexRep(node, fin, REC, wordStack, stackpos)
            || lexMaybe(node, fin, REC, wordStack, stackpos)
            || lexAny(node, fin, REC, wordStack, stackpos)
            || lexGroup(node, fin, REC, wordStack, stackpos);
        
        node.syntax.dirtier--;
        return ok;
    }
    
    private long updateRec(Verb node, long REC)
    {
        if (node.rec != null && node.rec[0] != null) {
            node.rec[0].RECCNT++;
            REC = node.rec[0].mask(REC);
        }
        return REC;
    }

    private boolean lexRep(Verb node, FeatureInputStream fin, long REC, List<WordRec> wordStack, int[] stackpos) throws IOException
    {
        if (!isRep(node)) { return false; }
        
        int consumed = 0;
        boolean go = true;
        while(go)
        {
            Verb c = deepCopy(node.form.get(0));
            REC = updateRec(node, REC);
            int oldStackpos = stackpos[0];
            go = lexTree(c, fin, REC, wordStack, stackpos);
            consumed += (go?1:0);
            if (go) {
                node.matter.add(c);
            } else {
                if (node.rec != null && node.rec[0] != null) {
                    node.rec[0].RECCNT--;
                }
                stackpos[0] = oldStackpos;
            }
        }
        if (consumed > 0)
        {
            node.postBuildMatter();
            return true;
        }
        return false;
    }
    private boolean lexMaybe(Verb node, FeatureInputStream fin, long REC, List<WordRec> wordStack, int[] stackpos) throws IOException
    {
        if (!isMaybe(node)) { return false; }
        
        Verb c = deepCopy(node.form.get(0));
        REC = updateRec(node, REC);
        int oldStackpos = stackpos[0];
        if (lexTree(c, fin, REC, wordStack, stackpos)) {
            node.matter.add(c);
            node.postBuildMatter();
            return true;
        }
        if (node.rec != null && node.rec[0] != null) {
            node.rec[0].RECCNT--;
        }
        stackpos[0] = oldStackpos;
        return false;
    }
    private boolean lexAny(Verb node, FeatureInputStream fin, long REC, List<WordRec> wordStack, int[] stackpos) throws IOException
    {
        if (!isAny(node)) { return false; }
        
        REC = updateRec(node, REC);
        int oldStackpos = stackpos[0];
        for(Verb c_ : node.form)
        {
            Verb c = deepCopy(c_);
            long rec = REC;
            if (lexTree(c, fin, REC, wordStack, stackpos)) {
                node.matter.add(c);
                node.postBuildMatter();
                // what if this branch is pruned somewhere upwards but next child would give a better branch ??? basically bad form
                return true;
            }
            stackpos[0] = oldStackpos;
        }
        return false;
    }
    private boolean lexGroup(Verb node, FeatureInputStream fin, long REC, List<WordRec> wordStack, int[] stackpos) throws IOException
    {
        if (!isGroupLike(node)) { return false; }
        
        REC = updateRec(node, REC);
        int oldStackpos = stackpos[0];
        int prevStackpos = oldStackpos;
        boolean consumedSmth = false;
        int n = node.form.size();
        for(int i = 0; i < n; i++)
        {
            Verb c_ = node.form.get(i);
            Verb c = deepCopy(c_);
            long rec = REC;
            //if (i == n - 1) c.syntax.dirtier--; // nok
            boolean didMatch = lexTree(c, fin, REC, wordStack, stackpos);
            //if (i == n - 1) c.syntax.dirtier++;
            if (!didMatch) {
                if (isRep(c) || isMaybe(c) || allConditional(c.form, 0)) {
                    stackpos[0] = prevStackpos;
                    continue;
                }
                if (consumedSmth && allConditional(node.form, i)) {
                    stackpos[0] = prevStackpos;
                    c.postBuildMatter();
                    return true;
                }
                stackpos[0] = oldStackpos;
                return false;
            }
            node.matter.add(c);
            prevStackpos = stackpos[0];
            consumedSmth = true;
        }
        if (consumedSmth) {
            node.postBuildMatter();
        }
        return consumedSmth;
    }
    
    private boolean allConditional(List<Verb> form, int offset)
    {
        if (form == null || form.isEmpty()) { return false; }
        boolean ok = true;
        for(int i = offset; i < form.size(); i++)
        {
            Verb c = form.get(i);
            if (isMaybe(c) || isRep(c)) {}
            else if (isAny(c)) {
                boolean okAny = false;
                for(int j = 0; j < c.form.size(); j++)
                {
                    if (c.syntax.dirtier > MAX_RECURSION) { continue; }
                    c.syntax.dirtier++;
                    if (allConditional(c.form.get(j).form, 0)) { okAny = true;  c.syntax.dirtier--;  break; }
                    c.syntax.dirtier--;
                }
                ok &= okAny;
            }
            else if (isGroupLike(c)) { ok = allConditional(c.form, 0); }
            else { ok = false; }
            
            if (!ok) { break; }
        }
        return ok;
    }

    private boolean lexTerminal(Verb node, FeatureInputStream fin, long REC, List<WordRec> wordStack, int[] stackpos) throws IOException
    {
        if (!isTerminal(node)) { return false; }
        
        WordRec word;
        if (stackpos[0] < wordStack.size()) {
            
            word = wordStack.get(stackpos[0]);
            if (!node.syntax.terminal.matcher(word.word).matches()) {
                return false;
            }
        }
        else
        {
            if (whitespaceReg != null) {
                whitespaceReg.readItem(fin);
            }
            fin.mark();
            word = new WordRec();
            word.streamPos = fin.snapPos();
            word.word = node.syntax.sterminal.readItem(fin);
            if (word.word == null) {
                fin.rewind();
                return false;
            }
            word.record = REC;
            wordStack.add(word);
        }
        stackpos[0]++;
        if (node.word != null) { // recursed
            node = node.instancedCopy(false);
            node.prev.matter.add(node);
        }
        node.word = word.word;
        node.position = word.streamPos;
        node.record = word.record;
        node.postBuildMatter();
        return true;
    }

    
    
    
    
    private Verb deepCopy(Verb a) {
        Verb r = deepCopyPhaseOne(a);
        deepCopyRecursions(r);
        return r;
    }
    final private String PLACEH = ">REPLACE<".intern();
    private Verb deepCopyPhaseOne(Verb a) {
        Verb b = a.instancedCopy(false);
        int i = 0;
        for(Verb ac : a.form) {
            if (a.rec != null && a.rec[i] != null) {b.form.add(ac);} // copy a ready-made recursion
            else if (ac.syntax.isDirty()) {
                Verb t = new Verb();
                t.syntax = ac.syntax;
                t.word = PLACEH;
                t.prev = b;
                t.childIndex = b.form.size();
                b.form.add(t);
            } else {
                ac.syntax.setDirty(true);
                Verb c = deepCopyPhaseOne(ac);
                b.form.add(c);
                c.prev = b;
                ac.syntax.setDirty(false);
                if (isRep(a))   // copy only the prototype at [0], not instances following
                    {break;}
            }
            i++;
        }
        return b;
    }
    
    private long recids = 0L;
    private void deepCopyRecursions(Verb a) {
        if (a.word == PLACEH) {
            Verb P = a.prev;
            while(P != null) {
                if (P.syntax == a.syntax) {
                    a.prev.form.set(a.childIndex, P);
                    if (a.prev.rec == null) {
                        a.prev.rec = new REC[a.prev.form.size()];
                    }
                    if (a.prev.rec[a.childIndex] == null) {
                        a.prev.rec[a.childIndex] = new REC(recids++);
                    }
                    return;
                }
                P = P.prev;
            }
            bff.RT.throwRte("couldn't finish syntactical recursion of " + a.prev);
        }
        for(Verb c : a.form)
            if (!c.syntax.isDirty() || c.word == PLACEH) {
                c.syntax.setDirty(true);
                deepCopyRecursions(c);
                c.syntax.setDirty(false);
            }
    }
    

    
    public static boolean isRep(Verb a) { return Syntax.isRep(a.syntax); }
    public static boolean isAny(Verb a) { return Syntax.isAny(a.syntax); }
    public static boolean isMaybe(Verb a) { return Syntax.isMaybe(a.syntax); }
    public static boolean isGroup(Verb a) { return Syntax.isGroup(a.syntax); }
    public static boolean isGroupLike(Verb a) { return a.syntax.structure != null; }
    public static boolean isTerminal(Verb a) { return Syntax.isTerminal(a.syntax); }

    
    
    
    
    public static void simplifyMatter(Verb a)
    {
        if (a.matter != null) {
            for(int i = 0; i < a.matter.size(); i++)
            {
                Verb child = a.matter.get(i);
                while ((!isRep(child) && !isMaybe(child) && isGroupLike(child)) && child.matter.size() == 1) {
                    child = child.matter.get(0);
                    a.matter.set(i, child);
                }
                simplifyMatter(child);
            }
        }
    }
}
