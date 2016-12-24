package bff.z;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

public class AddList<T> extends ArrayList<T> {

    @Override public T          set(int index, T element)   { throw new UnsupportedOperationException(); }
    @Override public T          remove(int index)           { throw new UnsupportedOperationException(); }
    @Override public boolean    remove(Object o)            { throw new UnsupportedOperationException(); }
    @Override public boolean    removeAll(Collection<?> c)  { throw new UnsupportedOperationException(); }
    @Override public void       removeRange(int i, int j)   { throw new UnsupportedOperationException(); }
    @Override public boolean    retainAll(Collection<?> c)  { throw new UnsupportedOperationException(); }
    @Override public ListIterator<T> listIterator()         {return listIterator(0);}
    @Override public ListIterator<T> listIterator(final int index) {
        return new ListIterator<T>() {
            private final ListIterator<? extends T> i = AddList.super.listIterator(index);
            @Override public boolean hasNext()     {return i.hasNext();}
            @Override public T next()              {return i.next();}
            @Override public boolean hasPrevious() {return i.hasPrevious();}
            @Override public T previous()          {return i.previous();}
            @Override public int nextIndex()       {return i.nextIndex();}
            @Override public int previousIndex()   {return i.previousIndex();}
            @Override public void remove() { throw new UnsupportedOperationException(); }
            @Override public void set(T e) { throw new UnsupportedOperationException(); }
            @Override public void add(T e) { throw new UnsupportedOperationException(); }
        };
    }
}
