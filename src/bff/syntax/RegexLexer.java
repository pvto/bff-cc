package bff.syntax;

import bff.RT;
import bff.z.Trees;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pvto https://github.com/pvto
 */
public class RegexLexer {

    
    
    public RegexLexer(Syntax S) {
        Syntax root = S;
        map0(root);
        List<Syntax> flat = Trees.flatten(root);
        for(Syntax s : flat)
            map1(s);
    }
    
    private void map0(Syntax root) {
        if (root.dirty)
            return;
        if (root.sterminal != null) {
            root.possibleTerminalContinuations = RT.newList((Syntax.sT)root);
        }
        root.dirty = true;
        root.possibleTerminalContinuations = new ArrayList<>(2);
        for(Syntax s : root.structure) {
            //root.possibleTerminalContinuations.addAll(map())
            map0(s);
        }
        root.dirty = false;
    }

    private List<Syntax.sT> map1(Syntax root) {
        if (root.dirty)
            return null;
        if (root.sterminal != null)
            return null;
        root.dirty = true;
        for(Syntax s : root.structure) {
            List<Syntax.sT> children = map1(s);
            if (children != null) {
                root.possibleTerminalContinuations.addAll(children);
            }
        }
        root.dirty = false;
        return root.possibleTerminalContinuations;
    }
    /*
A #a   a 
B #b   b
X BY   b
Y AX   ab
S Y*   ab
    
    */
}
