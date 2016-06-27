package jmp2016.memoryobject;

import java.util.List;

public class MemoryObject {

    protected byte[] hugeField;
    protected String hugeString;

    public MemoryObject(byte[] hugeField) {
        this.hugeField = hugeField;
    }

    public MemoryObject(String hugeString) {
        this.hugeString = hugeString;
    }

    public int getSize() {
        if (hugeField != null) {
            return hugeField.length;
        } else if (hugeString != null) {
            return hugeString.length();
        } else {
            return 0;
        }
    }

    public static long getSize(List<MemoryObjectWithReferences> objectList) {
        long size = 0;
        for (MemoryObject object : objectList) {
            size += object.getSize();
        }
        return size;
    }
}