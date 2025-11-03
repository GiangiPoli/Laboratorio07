package it.unibo.inner.impl;

import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {
    
    //Class Fields
    private T[] elems;
    private Predicate<T> filter;
    //Class Constructor
    public IterableWithPolicyImpl( T[] elements ) {
        this(elements, new Predicate<T>() {
            @Override
            public boolean test(T elem) {
                return true;
            }
        });
    }

    public IterableWithPolicyImpl( T[] elements, Predicate<T> filter) {
        this.elems = elements;
        this.filter = filter;
    }

    //Class Method
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }


    public InnerClass iterator() {
        return new InnerClass();
    }

    private class InnerClass implements Iterator<T> {

        //Class Fields
        private int index;

        @Override
        public boolean hasNext() {
            int tmp_index = this.index + 1;

            while( this.index < IterableWithPolicyImpl.this.elems.length ) {
                if ( filter.test(IterableWithPolicyImpl.this.elems[this.index]) ) {
                    return tmp_index <= IterableWithPolicyImpl.this.elems.length;
                }
                this.index++;
            }
            return false;
        }

        @Override
        public T next() {

            return IterableWithPolicyImpl.this.elems[this.index++];
        }
        
    }

}
