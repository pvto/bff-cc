package bff.z;

import java.util.ArrayList;
import java.util.List;

public class Trees {
    
    
    public interface Tree<S> {
        Iterable<Tree<S>> getBranches();
        <S> S getApple();
    }
    
    
    
    public static <T> List<T> flatten(Tree<T> tree) {
        List<T> ret = new ArrayList<>();
        flatten(tree, ret);
        return ret;
    }
    
    public static <T> void flatten(Tree<T> tree, List<T> ret) {
        for(Tree<T> branch : tree.getBranches()) {
            T apple = branch.getApple();
            if (apple != null)
                ret.add(branch.getApple());
            flatten(branch, ret);
        }
    }
}
