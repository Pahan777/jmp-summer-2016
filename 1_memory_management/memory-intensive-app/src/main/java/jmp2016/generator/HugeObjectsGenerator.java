package jmp2016.generator;

import jmp2016.memoryobject.MemoryObject;

import java.util.List;

public class HugeObjectsGenerator extends AbstractMemoryObjectGenerator {

    public HugeObjectsGenerator(long referenceHoldTime, int objectsPerSecond) {
        super(referenceHoldTime, objectsPerSecond);
        setName("HugeObjectsGenerator[" + referenceHoldTime + "ms," + objectsPerSecond + "/s]");
    }

    @Override
    protected List<MemoryObject> createObjects(int count) {
        return factory.createHugeSizeObjectsList(count);
    }
}