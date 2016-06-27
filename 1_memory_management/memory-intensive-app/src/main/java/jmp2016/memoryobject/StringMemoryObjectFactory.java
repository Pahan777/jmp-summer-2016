package jmp2016.memoryobject;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * For testing string deduplication feature.
 * We need to make short strings
 */
public class StringMemoryObjectFactory extends AbstractMemoryObjectFactory {

    /**
     * Generates strings like: 0000000111111100000000000001111111
     */
    @Override
    public MemoryObject createObject(int size) {
        int chunkSize = size / 20;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (new Random().nextBoolean()) {
                builder.append(RandomStringUtils.random(chunkSize, "0"));
            } else {
                builder.append(RandomStringUtils.random(chunkSize, "1"));
            }
        }

        return new MemoryObject(builder.toString());
    }
}