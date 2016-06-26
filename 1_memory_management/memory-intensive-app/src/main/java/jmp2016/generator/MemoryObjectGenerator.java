package jmp2016.generator;

import jmp2016.generator.reference.ExpiredListReference;
import jmp2016.memoryobject.MemoryObject;
import jmp2016.memoryobject.MemoryObjectFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemoryObjectGenerator extends Thread {

    protected final MemoryObjectFactory factory = new MemoryObjectFactory();

    /**
     * How much time object keeps reference
     */
    protected final long referenceHoldTime;
    protected final int objectsPerSecond;
    protected final int singleObjectSize;

    /**
     * Set of expired references
     * Each reference refers to list of MemoryObjects
     */
    protected Set<ExpiredListReference<MemoryObject>> references = new HashSet<ExpiredListReference<MemoryObject>>();

    public MemoryObjectGenerator(long referenceHoldTime, int objectsPerSecond, int singleObjectSize) {
        this.referenceHoldTime = referenceHoldTime;
        this.objectsPerSecond = objectsPerSecond;
        this.singleObjectSize = singleObjectSize;
        setName(referenceHoldTime + " s");
    }

    @Override
    public void run() {
        while (true) {
            // Create objects
            List<MemoryObject> objects = factory.createObjects(objectsPerSecond / 10, singleObjectSize);
            // Add references
            references.add(new ExpiredListReference<>(objects, referenceHoldTime));
            // Expire references if any

            expireReferences();

            printAllocatedObjectsStat(references);

            try {
                // Objects are created 10 times per second
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void expireReferences() {
        Set<ExpiredListReference<MemoryObject>> expiredReferenceObjectLinks = new HashSet<ExpiredListReference<MemoryObject>>();

        long expiredObjectsSize = 0;
        long expiredObjectsCount = 0;

        for (ExpiredListReference<MemoryObject> listReference : references) {
            long size = MemoryObject.getSize(listReference.get());
            int count = listReference.get().size();

            if (listReference.expire()) {
                expiredReferenceObjectLinks.add(listReference);

                expiredObjectsSize += size;
                expiredObjectsCount += count;
            }
        }

        log("Expired " + formatSize(expiredObjectsSize, expiredObjectsCount));

        references.removeAll(expiredReferenceObjectLinks);
    }

    protected void printAllocatedObjectsStat(Set<ExpiredListReference<MemoryObject>> references) {
        long size = 0;
        long count = 0;

        for (ExpiredListReference<MemoryObject> reference : references) {
            size += MemoryObject.getSize(reference.get());
            count += reference.get().size();
        }

        log("Allocated " + formatSize(size, count));
    }

    protected String formatSize(long size, long count) {
        String sizeString = "";
        if (size > 1000000) {
            sizeString = size / 1000000 + " MB";
        } else if (size > 1000) {
            sizeString = size / 1000 + " KB";
        } else {
            sizeString = size + " B";
        }

        return "count: " + count + ", size: " + sizeString;
    }

    protected void log(String message) {
//        System.out.println(getName() + "> " + new SimpleDateFormat("hh:mm:ss SSS").format(new Date()) + " " + message);
    }
}