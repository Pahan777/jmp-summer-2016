package jmp2016.generator;

import javafx.scene.SubScene;
import jmp2016.generator.reference.ExpiredListReference;
import jmp2016.memoryobject.MemoryObject;
import jmp2016.memoryobject.MemoryObjectFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractMemoryObjectGenerator extends Thread {

    protected final MemoryObjectFactory factory = new MemoryObjectFactory();

    /**
     * How much time object keeps reference
     */
    protected final long referenceHoldTime;
    protected final int objectsPerSecond;

    /**
     * Set of expired references
     * Each reference refers to list of MemoryObjects
     */
    protected Set<ExpiredListReference<MemoryObject>> references = new HashSet<ExpiredListReference<MemoryObject>>();

    protected AbstractMemoryObjectGenerator(long referenceHoldTime, int objectsPerSecond) {
        this.referenceHoldTime = referenceHoldTime;
        this.objectsPerSecond = objectsPerSecond;
    }

    @Override
    public void run() {
        while (true) {
            // Create objects
            List<MemoryObject> objects = createObjects(objectsPerSecond / 10);
            // Add references
            references.add(new ExpiredListReference<MemoryObject>(objects, referenceHoldTime));
            // Expire references if any
            expireReferences();

            printAllocatedObjectsSize();

            try {
                // Objects are created 10 times per second
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract List<MemoryObject> createObjects(int count);

    protected void expireReferences() {
        Set<ExpiredListReference<MemoryObject>> expiredReferences = new HashSet<ExpiredListReference<MemoryObject>>();

        for (ExpiredListReference<MemoryObject> listReference : references) {
            // TODO calculate size of expired objects?
            if (listReference.expire()) {
                expiredReferences.add(listReference);
            }
        }

        references.removeAll(expiredReferences);
    }

    protected void printAllocatedObjectsSize() {
        long size = 0;
        for (ExpiredListReference<MemoryObject> reference : references) {
            size+= MemoryObject.getSize(reference.get());
        }

        String sizeString = "";
        if (size > 1000000) {
            sizeString = size / 1000000 + " MB";
        } else if (size > 1000) {
            sizeString = size / 1000 + " KB";
        } else {
            sizeString = size + " B";
        }

        System.out.println(getName() + " allocated objects size=" + sizeString);
    }
}