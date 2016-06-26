package jmp2016.generator.reference;

public class ExpiredReference<T> {

    protected final long expirationTime;
    protected T reference;

    public ExpiredReference(T reference, long holdTime) {
        expirationTime = System.currentTimeMillis() + holdTime;
        this.reference = reference;
    }

    public boolean expire() {
        if (System.currentTimeMillis() >= expirationTime) {
            reference = null;
            return true;
        }

        return false;
    }

    public T get() {
        return reference;
    }
}