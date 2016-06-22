package jmp2016.generator;

import jmp2016.memoryobject.MemoryObject;

import java.util.List;

public class RandomObjectsGenerator extends AbstractMemoryObjectGenerator {

    public RandomObjectsGenerator(long referenceHoldTime, int objectsPerSecond) {
        super(referenceHoldTime, objectsPerSecond);
        setName("RandomObjectsGenerator[" + referenceHoldTime + "ms," + objectsPerSecond + "/s]");
    }

    @Override
    protected List<MemoryObject> createObjects(int count) {
        return factory.createRandomSizeObjectsList(count);
    }
}