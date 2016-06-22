package jmp2016.generator.reference;

import java.util.List;

public class ExpiredListReference<T> {

    protected final long expirationTime;
    protected List<T> listReference;

    public ExpiredListReference(List<T> list, long holdTime) {
        expirationTime = System.currentTimeMillis() + holdTime;
        listReference = list;
    }

    public boolean expire() {
        if (System.currentTimeMillis() >= expirationTime) {
            listReference = null;
            return true;
        }
        return false;
    }

    public List<T> get() {
        return listReference;
    }
}
