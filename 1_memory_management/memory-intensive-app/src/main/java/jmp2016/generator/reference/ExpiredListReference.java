package jmp2016.generator.reference;

import java.util.List;

public class ExpiredListReference<T> extends ExpiredReference<List<T>> {

    public ExpiredListReference(List<T> reference, long holdTime) {
        super(reference, holdTime);
    }
}
