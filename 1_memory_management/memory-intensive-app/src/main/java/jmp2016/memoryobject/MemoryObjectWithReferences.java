package jmp2016.memoryobject;

import java.util.ArrayList;
import java.util.List;

public class MemoryObjectWithReferences extends MemoryObject {

    protected List<MemoryObject> references = new ArrayList<>();

    public MemoryObjectWithReferences(byte[] hugeField) {
        super(hugeField);
    }

    public MemoryObjectWithReferences(String hugeString) {
        super(hugeString);
    }

    public void addReference(MemoryObject reference) {
        references.add(reference);
    }
}