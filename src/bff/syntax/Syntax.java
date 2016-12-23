package bff.syntax;

import bff.RT;
import bff.Scope;
import bff.io.StreamRegex;
import bff.z.Trees;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** An abstract superclass for sentences of syntactic structures. 
 * 
 *  This class contains a context free parser that creates new intermediate
 * syntax trees from bits and pieces. */
public class Syntax extends bff.$A implements Trees.Tree<Syntax> {


    //syntax definition stx: (1) # starts a regexp (terminal);
    //                       (2) x (one character) refers back to another node; 
    //                       (3) x|y receives either of x or y;
    //                       (4) * (kleene) marks indefinite repetition (0..n) of the preceding item;
    //                       (5) ? repeats 0 or 1 times of the preceding item;
    //                       (6) < and > bound a composite group; you can use ?*| after it, or even nest groups if you need to (though it looks a bit confusing)
    //Contention is resolved this way (will be anyway, if the idea won't change).
    // 1. compute the maximum common terminal count 'c' for A and B, reading from left to right, where A precedes B as in A|B or in A?B or A*B
    // 2. if c>0, 
    //      2.1. if it was A|B and B is longer -> switch the places of A and B
    //      2.2. mark whichever of A and B retains the first position, with LOOKUP=c+1
    // 3. if c(A,C)>c(A,B), then reorder like this: C|A|B
    // 4. if reordering is impossible as in {A=aa,B=aab,C=A*B}, such syntax is treated as malformed
    static public class     s_LB extends sT {{      x= '(';  s= "#\\(";         }}
    static public class     s_RB extends sT {{      x= ')';  s= "#\\)";         }}
    static public class     s_LC extends sT {{      x= '{';  s= "#{";           }}
    static public class     s_RC extends sT {{      x= '}';  s= "#}";           }}
    static public class     s_LD extends sT {{      x= '[';  s= "#\\[";         }}
    static public class     s_RD extends sT {{      x= ']';  s= "#\\]";         }}
    static public class     s__n extends sT {{      x= '_';  s= "#[\\s]+";      }}
    static public class     s___ extends Syntax {{      x= ' ';  s= "_?";      }}
    static public class     s__c extends sT {{      x= ',';  s= "#\\s*,\\s*";   }}
    static public class     s_o extends  sO {{      x= 'o';  s= "#[!-'*-/:<-@\\^|~\\u2100-\\u239A]";      }}
    static public class     s_Ni extends sN {{      x= 'i';  s= "#[+\\-]?([0-9]{1-10}|0x[0-9a-fA-F]{1-8})";      }}
    static public class     s_Nl extends sN {{      x= 'l';  s= "#[+\\-]?([0-9]{1-19}|0x[0-9a-fA-F]{1-16})";      }}
    static public class     s_Nf extends sN {{      x= 'f';  s= "#[+\\-]?([0-9]+|[0-9]*.[0-9]+|[0-9]+e[0-9]+)[fF]";      }}
    static public class     s_Nd extends sN {{      x= 'd';  s= "#[+\\-]?([0-9]+|[0-9]*.[0-9]+|[0-9]+e[0-9]+)[dD]?";      }}
    static public class     s_s extends  sS {{      x= 's';  s= "#[A-Za-z?_$\\u00C0-\\u02B8\\u0370-\\u03FF][\\w!?\\-$\\u00C0-\\u02B8\\u0370-\\u03FF]*";      }}    // std. symbol definition: ascii,latin,greek letters mostly
    static public class     s_se extends  sE {{     x= 'S';  s= "s";            }}  // an expression that is a symbol
    static public class     s_beb extends sE {{     x= 'b';  s= "( e )";        }}
    static public class     s_oe extends  sE {{     x= '-';  s= "o e";          }}
    static public class     s_eoe extends sE {{     x= '+';  s= "e o e";        }}
    static public class     s_eo extends  sE {{     x= '/';  s= "e o";          }}
    static public class    s_eoeoe extends sE{{     x= ':';  s= "e o e o e";    }}
    static public class     s_f extends   sE {{     x= 'F';  s= "s ( e<,e>* )";    }}
    static public class     s_e extends   sE {{     x= 'e';  s= "b|F|S|-|:|+|/";  }}
    static public class     s_dEd extends Syntax {{     x= 'D';  s= "[ e* ]";       }}
    static public class     s_ssbSbEbb extends Syntax {{x= '@';  s= "s_s ( s* ( e* ) )"; }}

    
    public static boolean isRep(Syntax s) { return bff.RT.classIs(s, Syntax.sRepeat.class); }
    public static boolean isAny(Syntax s) { return bff.RT.classIs(s, Syntax.sAny.class); }
    public static boolean isMaybe(Syntax s) { return bff.RT.classIs(s, Syntax.sMaybe.class); }
    public static boolean isGroup(Syntax s) { return bff.RT.classIs(s, Syntax.sGroup.class); }
    public static boolean isTerminal(Syntax s) { return bff.RT.classIs(s, Syntax.sT.class); }

    //syntax categories
    /*all terminal sentences should inherit this*/      static public class sT extends Syntax{} 
    /*any symbols should inherit this; 
      only one variation, x= 's', 
      should be registered at a holder*/                static public class sS extends sT{}
    /*any numbers should inherit this*/                 static public class sN extends sT{}
    /*operator-defining sentences should inherit this; 
      only one variation, x= 'o',
      should be registered at a holder*/                static public class sO extends sT{}
    /*all expression sentences should inherit this*/    static public class sE extends Syntax{}
    static public class sRepeat extends Syntax{{        x= '*';}}
    static public class sAny extends    Syntax{{        x= '|';}
            public sAny(int count)          { structure = new Syntax[count];}}
    static public class sMaybe extends  Syntax{{        x= '?';}}
    static public class sGroup extends  Syntax{{        x= '<';}
            public sGroup(int count)          { structure = new Syntax[count];}
            public sGroup(){}}


    
    // each syntax node has structure or is a terminal construct
    
    /** a semi-unique symbol */ public char x;
    /** describes structure */  public String s;
                                public Syntax[] structure;  // filled by init()
    /** for a terminal constr*/ public Pattern terminal;
    /** likewise */             public StreamRegex sterminal;
                                public List<Syntax.sT> possibleTerminalContinuations;
                                public RegexMapper<Syntax.sT> mapper;
    /** */                      public int lookup = 0;
        /** runtime instruments*/   public bff.$ parser;  public <T extends Syntax> T parser$(bff.$ parser) {this.parser = parser;  return (T)this;}
    /** passed on to lexer... */public bff.$ eval;
                                public bff.$[] evalTmp;
    @Override public String toString() { return x + (lookup>1?"[L"+lookup+"]":"") + " -> " + (s!=null?s+" ":"") + this.getClass().getSimpleName(); }
    

    
    static public class Holder {
        public int count = 0;
        public final Syntax[] syntaxes = new Syntax[1024];
        public final void addSyntax(Syntax sy) {
            if (syntaxes[sy.x] != null) {
                if (syntaxes[sy.x].s.equals(sy.s) && (syntaxes[sy.x].getClass().isAssignableFrom(sy.getClass())))
                    {return;}
                bff.RT.throwRte("attempted redefinition of syntax '"+sy+" - found: "+syntaxes[sy.x]);  
            }
            syntaxes[sy.x] = sy;  count++;  }
        public Holder defaults$() {
            for(Syntax sy : new Syntax[]{
                new s_LB(), new s_RB(), new s_LC(), new s_RC(), new s_LD(), new s_RD(),
                new s__n(), new s_o(), new s_s(), 
                new s_se(), new s_beb(), new s_oe(), new s_eoe(), new s_eo(), new s_eoeoe(),
                new s_e(), new s_dEd()
            }) addSyntax(sy);
            return this; }
        public final void initAll() { for(Syntax sy : syntaxes) if (sy != null) sy.init(this); }
        public int count() { return count; }
    }
    
    
    
    
// an aid to eliminate syntax juggling
    private boolean locked = false;
    public synchronized void lock() {
        if (dirty) {return;}
        dirty = true;
        locked = true;
        if (structure != null) {for(Syntax sy : structure) sy.lock();}
        dirty = false;
    }

//Parser for the mini-language:
//.................................
    public boolean dirty = false;
    public final synchronized void setDirty(boolean d) { this.dirty = d; }
    public final boolean isDirty() { return dirty; }
    public int dirtier = 0;
    public synchronized void init(Holder h) {
        init_(h);
//        collisions(h, this);
    }
    private void init_(Holder h) {
        if (dirty) {return;}    // prevent cycles
        if (locked) bff.RT.throwRte("attempt to re-lock syntax node ["+this+"]");
        if (structure != null || terminal != null) {return;}
        if (s.charAt(0) == '#') {
            terminal = Pattern.compile(s.substring(1));
            sterminal = new StreamRegex(s.substring(1));
            return;
        }
        dirty = true;
        structure = parseStructure2(h, s, new int[]{0});
        dirty = false;
        
        initVerbs(this, evalTmp, 0, -1);
    }
    
    private Syntax[] parseStructure2(Holder h, String s, int[] a) {
        ArrayList<Syntax> ss = new ArrayList<>();
        int z = 0;
        for(; a[0] < s.length(); a[0]++) {
            int back = a[0]-1;
            int prev = back < 0 ? - 1 : s.charAt(back);
            char x = s.charAt(a[0]);
            boolean ok = 
                    ('>' == x)
                    || addNonNull(ss, parseGroup(h, s, a))
                    || nestLast(ss, x=='*' ? new sRepeat():null)
                    || nestLast(ss, x=='?' ? new sMaybe():null)
                    || ('|' == x)
                    || addNonNull(ss, parseItem(h, s, a))
                    ;
            if ('>' == x) { 
                break;
            }
            if (ok && ('|' == prev || ss.size() > 1 && isAny(ss.get(ss.size()-2)))) {
                nestLastInPrevAny(ss, back);
            }
        }
        return ss.toArray(new Syntax[ss.size()]);
    }
    private Syntax parseItem(Holder h, String s, int[] a) {
        char c = s.charAt(a[0]);
        Syntax x = h.syntaxes[c];
        if (x == null)
            bff.RT.throwRte("syntax "+c+" not registered, ref. from "+this+" col "+a[0]);
        x.init_(h);
        return x;
    }
    private boolean nestLast(ArrayList<Syntax> ss, Syntax s) {
        if (s == null) return false;
        if (ss.isEmpty()) bff.RT.throwRte(s.x + " can't be applied without parameter");
        Syntax x = ss.get(ss.size() -1); 
        if (x instanceof sAny) {
            // * takes precedence over | so a|<<b>|<c>>* will repeat <> and not |
            s.structure = new Syntax[]{ x.structure[x.structure.length-1] };
            x.structure[x.structure.length-1] = s;
        } else {
            s.structure = new Syntax[]{ss.remove(ss.size()-1)};
            ss.add(s);
        }
        return true; }
    private void nestLastInPrevAny(ArrayList<Syntax> ss, int back) {
        if (ss.size() < 2)
            bff.RT.throwRte("| must be preceded by expression at column "+back+" of "+this);
        Syntax any = ss.get(ss.size()-2);
        if (!(any instanceof sAny)) {
            Syntax tmp = any;
            any = new sAny(1);
            any.structure[0] = tmp;
            ss.set(ss.size()-2, any);
        }
        any.structure = bff.Arf.concat(any.structure, ss.remove(ss.size()-1));
    }
    private boolean addNonNull(ArrayList<Syntax> ss, Syntax s) {
        if (s != null) { ss.add(s); return true; }  else { return false; }}
    private Syntax parseGroup(Holder h, String s, int[] a) {
        if ('<' != s.charAt(a[0]))
            return null;
        a[0]++;
        Syntax[] list = parseStructure2(h, s, a);
        if (s.length() <= a[0] || '>' != s.charAt(a[0]))
            bff.RT.throwRte("unterminated group, > missing at column " + a[0] + " of " + this);
        Syntax ret = new sGroup(list.length);
        ret.structure = list;
        return ret;
    }


    private static int initVerbs(Syntax s, bff.$[] evalTmp, int offset, int structOffset)
    {
        if (evalTmp == null)
            return 0;
        if (offset >= evalTmp.length)
            return 0;
        if (s.eval != null)
            return 0;
        if (s.evalTmp != null && s.evalTmp != evalTmp)
        {
            if (!s.dirty) {
                s.dirty = true;
                initVerbs(s, s.evalTmp, 0, -1);
                s.dirty = false;
            }
            return 0;
        }

        
        int consumed = 0;
        if (structOffset == -1) 
        {
            s.eval = evalTmp[offset++];
            structOffset++;
            consumed++;
        }

        while(structOffset < s.structure.length)
        {
            Syntax child = s.structure[structOffset++];
            if (offset < evalTmp.length) {
                int n = initVerbs(child, evalTmp, offset, -1);
                offset += n;
                consumed += n;
            } else if (child.evalTmp != null) {
                initVerbs(child, child.evalTmp, 0, -1);
            }
        }
        if (structOffset < s.structure.length)
            bff.RT.throwRte("Syntax " + s + ": needs more verbs (once you've started with them)");
        return consumed;
    }
    
    
    
    // methods for printing out the structure
    
    public void printStructure(PrintStream out) { printStructure_(out, 0); }
    
    private void printStructure_(PrintStream out, int indent) {
        out.print(bff.Arf.repeat(' ', indent));
        out.print(x);
        if (terminal != null)
            out.print("= "+terminal.toString());
        out.print("\r\n");
        synchronized(this) {
            if (dirty) {return;}
            dirty = true;
            if (structure != null) {
                for(Syntax s : structure)
                    s.printStructure_(out, indent+2);
                out.print("\r\n");
            }
            dirty = false;
        }
    }

    // methods for detecting lexing collisions
    
    
/*    
    static private void collisions(Holder h, s_ s) {
        if (s.structure == null) {s.lookup = 1;}
        if (s.lookup > 0) {return;}
        if (!s.isDirty())
            for(s_ c : s.structure) {
                s.setDirty(true);   // overlaps w/ maxDepth:setDirty 
                collisions(h, c);
                s.setDirty(false);
            }
        
        if (f.RT.classIs(s, s_.sAny.class)) {
            for(int i = 0; i < s.structure.length; i++) {
                for (int j = 0; j < s.structure.length; j++) {
                    if (i == j) {continue;}
                    if (s.structure[i] == s.structure[j]) {
                        s.structure = f.Arf.exclude(s.structure, j,j);
                        s.s = s.s.substring(0, j) + s.s.substring(j + 1);
                    } else {
                        int[] n = collisions(h, s, s, i, j, new LinkedList(), new LinkedList(), true);
                        s.structure[i].lookup = (n[0] > 1 ? n[0] : 1);
                    }
                }
            }
        } else {
            for(int i = 0; i < s.structure.length - 1; i++) {
                int j = i + 1;
                while(j < s.structure.length) {
                    if (mayVoid(s.structure[i])) {
                        int[] n = collisions(h, s, s, i, j, new LinkedList(), new LinkedList(), false);
                        s.structure[i].lookup = (n[0] > 1 ? n[0] : 1);
                    }
                    if (!mayVoid(s.structure[j++])) {break;}
                }
            }
        }
    }
    static private boolean mayVoid(s_ s) {  return f.RT.classIn(s, s_.sMaybe.class, s_.sRepeat.class); }
    static private int[] collisions(Holder h, s_ para, s_ parb, int a, int b, LinkedList astack, LinkedList bstack, boolean maySwitch) {
        if (para.structure[a] == parb.structure[b]) {
            if (para.structure[a].sterminal != null) { 
                return new int[]{astack.size() + 1, 0};
            }
            int d = maxDepth(para.structure[a], 0);
            return new int[]{d==Integer.MAX_VALUE? d : astack.size()+d, d};
        }
        else
        if (f.RT.classIs(para.structure[a], s_.sAny.class)) {
            
        }
        else
        if (mayVoid(para.structure[a])) {
            astack.push(para);
            int[] ret = collisions(h, para.structure[a], parb, 0, b, astack, bstack, maySwitch);
            astack.pop();
            return ret;
        }
        else {
            
        }
        return new int[]{0,0};
    }
    static private int maxDepth(s_ s, int dirtyTrack) {
        if (s.isDirty()) {
            if ((dirtyTrack & 2) == 0)
                f.RT.throwRte("head recursion from "+s);
            return Integer.MAX_VALUE;
        }
        if (s.sterminal != null) {return 1;}
        int cd = 0;
        s.setDirty(true);
        for(int i = 0; i < s.structure.length; i++) {
            s_ c = s.structure[i];
            int md = maxDepth(c, dirtyTrack | (i==0?1:2));
            cd = Math.max(cd, md);
            if (cd == Integer.MAX_VALUE)
                break;
        }
        s.setDirty(false);
        return cd == Integer.MAX_VALUE ? cd : cd + 1;
    }

    */
    
    
    
    @Override public Iterable<Trees.Tree<Syntax>> getBranches() { return RT.newIterable(this.structure); }
    @Override public <S> S getApple() { return (S)this; }

}
