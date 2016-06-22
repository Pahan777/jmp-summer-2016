package jmp2016.generator.reference;

public class ExpiredReference<T> {

    protected final long expirationTime;
    protected T reference;

    public ExpiredReference(T reference, long holdTime) {
        expirationTime = System.currentTimeMillis() + holdTime;
    }

    public void expire() {
        if (System.currentTimeMillis() >= expirationTime) {
            reference = null;
        }
    }

    public T get() {
        return reference;
    }
}
