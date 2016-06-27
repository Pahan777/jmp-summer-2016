package jmp2016.generator;

import jmp2016.generator.reference.ExpiredListReference;
import jmp2016.memoryobject.MemoryObjectWithReferences;
import jmp2016.memoryobject.factory.AbstractMemoryObjectFactory;
import jmp2016.memoryobject.MemoryObject;

import java.util.*;

public class MemoryObjectGenerator extends Thread {

    protected final AbstractMemoryObjectFactory factory;

    /**
     * How much time object keeps reference
     */
    protected final long referenceHoldTimeMs;
    protected final int objectsPerSecond;
    protected final int singleObjectSize;

    /**
     * Set of expired references
     * Each reference refers to list of MemoryObjects
     */
    protected Set<ExpiredListReference<MemoryObjectWithReferences>> references = new HashSet<>();

    public MemoryObjectGenerator(long referenceHoldTimeMs, int objectsPerSecond, int singleObjectSize, AbstractMemoryObjectFactory factory) {
        this.referenceHoldTimeMs = referenceHoldTimeMs;
        this.objectsPerSecond = objectsPerSecond;
        this.singleObjectSize = singleObjectSize;
        this.factory = factory;
        setName(referenceHoldTimeMs + " s");
    }

    @Override
    public void run() {
        while (true) {
            // Create objects
            List<MemoryObjectWithReferences> objects = factory.createObjects(objectsPerSecond / 200, singleObjectSize);

            createReferencesBetweenObjects(getAllExistedObjects(), objects, 10);
            // Add references
            references.add(new ExpiredListReference<>(objects, referenceHoldTimeMs));

            // Expire references if any
            expireReferences();

            printAllocatedObjectsStat(references);

            try {
                // Objects are created 10 times per second
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates many references between objects so GC will have more effort
     */
    private void createReferencesBetweenObjects(List<MemoryObjectWithReferences> objectsToAddReferences, List<MemoryObjectWithReferences> references, int referencesCountPerObject) {
        for (MemoryObjectWithReferences object : objectsToAddReferences) {
            for (int i = 0; i < referencesCountPerObject; i++) {
                object.addReference(getRandomObject(references));
            }
        }
    }

    protected MemoryObjectWithReferences getRandomObject(List<MemoryObjectWithReferences> list) {
        int randomNumber = Math.abs(new Random().nextInt());
        int randomObjectIndex = randomNumber % (list.size() - 1);
        return list.get(randomObjectIndex);
    }

    protected List<MemoryObjectWithReferences> getAllExistedObjects() {
        List<MemoryObjectWithReferences> objects = new ArrayList<>();
        for (ExpiredListReference<MemoryObjectWithReferences> reference : references) {
            objects.addAll(reference.get());
        }
        return objects;
    }

    protected void expireReferences() {
        Set<ExpiredListReference<MemoryObjectWithReferences>> expiredReferenceObjectLinks = new HashSet<>();

        long expiredObjectsSize = 0;
        long expiredObjectsCount = 0;

        for (ExpiredListReference<MemoryObjectWithReferences> listReference : references) {
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

    protected void printAllocatedObjectsStat(Set<ExpiredListReference<MemoryObjectWithReferences>> references) {
        long size = 0;
        long count = 0;

        for (ExpiredListReference<MemoryObjectWithReferences> reference : references) {
            size += MemoryObjectWithReferences.getSize(reference.get());
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
        // TODO print only once per second
//        System.out.println(getName() + "> " + new SimpleDateFormat("hh:mm:ss SSS").format(new Date()) + " " + message);
    }
}