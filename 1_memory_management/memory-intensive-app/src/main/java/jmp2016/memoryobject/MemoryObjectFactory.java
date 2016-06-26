package jmp2016.memoryobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryObjectFactory {

    public List<MemoryObject> createObjects(int numberOfObjects, int singleObjectSize) {
        List<MemoryObject> objects = new ArrayList<MemoryObject>();
        for (int i = 0; i < numberOfObjects; i++) {
            objects.add(createObject(singleObjectSize));
        }

        return objects;
    }

    public List<MemoryObject> createRandomSizeObjectsList(int size) {
        List<MemoryObject> objects = new ArrayList<MemoryObject>();
        for (int i = 0; i < size; i++) {
            objects.add(createObject(new Random().nextInt()));
        }

        return objects;
    }

    protected MemoryObject createObject(int size) {
        Random random = new Random();
        byte[] data = new byte[size];
        random.nextBytes(data);
        return new MemoryObject(data);
    }
}