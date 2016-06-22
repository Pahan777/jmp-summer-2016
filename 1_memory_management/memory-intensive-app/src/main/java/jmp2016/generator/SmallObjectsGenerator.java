package jmp2016.generator;

import jmp2016.memoryobject.MemoryObject;

import java.util.List;

public class SmallObjectsGenerator extends AbstractMemoryObjectGenerator {

    public SmallObjectsGenerator(long referenceHoldTime, int objectsPerSecond) {
        super(referenceHoldTime, objectsPerSecond);
        setName("SmallObjectsGenerator[" + referenceHoldTime + "ms," + objectsPerSecond + "/s]");
    }

    @Override
    protected List<MemoryObject> createObjects(int count) {
        return factory.createSmallSizeObjectsList(count);
    }
}