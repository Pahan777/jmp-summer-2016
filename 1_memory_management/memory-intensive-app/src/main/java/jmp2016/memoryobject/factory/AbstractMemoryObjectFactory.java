package jmp2016.memoryobject.factory;

import jmp2016.memoryobject.MemoryObject;
import jmp2016.memoryobject.MemoryObjectWithReferences;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMemoryObjectFactory {

    public List<MemoryObjectWithReferences> createObjects(int numberOfObjects, int singleObjectSize) {
        List<MemoryObjectWithReferences> objects = new ArrayList<>();
        for (int i = 0; i < numberOfObjects; i++) {
            objects.add(createObject(singleObjectSize));
        }

        return objects;
    }

    public abstract MemoryObjectWithReferences createObject(int size);
}