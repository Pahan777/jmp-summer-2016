package jmp2016.memoryobject.factory;

import jmp2016.memoryobject.MemoryObject;
import jmp2016.memoryobject.MemoryObjectWithReferences;

import java.util.Random;

public class ByteMemoryObjectFactory extends AbstractMemoryObjectFactory {

    @Override
    public MemoryObjectWithReferences createObject(int size) {
        Random random = new Random();
        byte[] data = new byte[size];
        random.nextBytes(data);
        return new MemoryObjectWithReferences(data);
    }
}