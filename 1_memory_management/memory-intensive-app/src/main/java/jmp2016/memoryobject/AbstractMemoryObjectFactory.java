package jmp2016.memoryobject;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMemoryObjectFactory {

    public List<MemoryObject> createObjects(int numberOfObjects, int singleObjectSize) {
        List<MemoryObject> objects = new ArrayList<MemoryObject>();
        for (int i = 0; i < numberOfObjects; i++) {
            objects.add(createObject(singleObjectSize));
        }

        return objects;
    }

    public abstract MemoryObject createObject(int size);
}