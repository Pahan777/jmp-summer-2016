package jmp2016.memoryobject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryObjectFactory {

    public List<MemoryObject> createSmallSizeObjectsList(int size) {
        List<MemoryObject> objects = new ArrayList<MemoryObject>();
        for (int i = 0; i < size; i++) {
            objects.add(createSmallSizeObject());
        }

        return objects;
    }

    public List<MemoryObject> createHugeSizeObjectsList(int size) {
        List<MemoryObject> objects = new ArrayList<MemoryObject>();
        for (int i = 0; i < size; i++) {
            objects.add(createHugeObject());
        }

        return objects;
    }

    public List<MemoryObject> createRandomSizeObjectsList(int size) {
        List<MemoryObject> objects = new ArrayList<MemoryObject>();
        for (int i = 0; i < size; i++) {
            objects.add(createRandomSizeObject());
        }

        return objects;
    }

    public MemoryObject createRandomSizeObject() {
        return createObject(new Random().nextInt());
    }

    public MemoryObject createSmallSizeObject() {
        return createObject(1000);
    }

    public MemoryObject createHugeObject() {
        return createObject(Integer.MAX_VALUE / 100);
    }

    protected MemoryObject createObject(int size) {
        Random random = new Random();
        byte[] data = new byte[size];
        random.nextBytes(data);
        return new MemoryObject(data);
    }
}