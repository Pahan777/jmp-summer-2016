package jmp2016.memoryobject;

import java.util.List;

public class MemoryObject {

    protected byte[] hugeField;

    public MemoryObject(byte[] hugeField) {
        this.hugeField = hugeField;
    }

    public int getSize() {
        return hugeField.length;
    }

    public static long getSize(List<MemoryObject> objectList) {
        long size = 0;
        for (MemoryObject object : objectList) {
            size += object.getSize();
        }
        return size;
    }
}