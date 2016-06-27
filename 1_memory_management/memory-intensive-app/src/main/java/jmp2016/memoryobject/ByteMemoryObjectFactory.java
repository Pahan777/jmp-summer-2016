package jmp2016.memoryobject;

import java.util.Random;

public class ByteMemoryObjectFactory extends AbstractMemoryObjectFactory {

    @Override
    public MemoryObject createObject(int size) {
        Random random = new Random();
        byte[] data = new byte[size];
        random.nextBytes(data);
        return new MemoryObject(data);
    }
}