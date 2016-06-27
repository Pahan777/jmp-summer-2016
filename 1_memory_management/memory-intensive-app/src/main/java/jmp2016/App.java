package jmp2016;

import jmp2016.generator.MemoryObjectGenerator;
import jmp2016.memoryobject.factory.AbstractMemoryObjectFactory;
import jmp2016.memoryobject.factory.ByteMemoryObjectFactory;
import jmp2016.memoryobject.factory.StringMemoryObjectFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static int objectsPerSecond = 0;
    // lifetime->count
    public static Map<Integer, Integer> objectGenerators = new HashMap<>();
    public static int singleObjectSizeKB = 0;
    public static AbstractMemoryObjectFactory objectsFactory;

    public static void main(String[] args) throws InterruptedException {
        parseArgs(args);

        System.out.println("Starting memory intensive application");
        System.out.println("Application creates " + objectsPerSecond + " per second of size " + singleObjectSizeKB + " bytes");
        System.out.println("With lifetimes " + objectGenerators);

        List<MemoryObjectGenerator> generators = new ArrayList<>();

        for (Integer lifetimeMs : objectGenerators.keySet()) {
            // Create generators
            for (int i = 0; i < objectGenerators.get(lifetimeMs); i++) {
                MemoryObjectGenerator generator =
                        new MemoryObjectGenerator(lifetimeMs, objectsPerSecond, singleObjectSizeKB, objectsFactory);
                generators.add(generator);
                generator.start();
            }
        }

        if (!generators.isEmpty()) {
            generators.get(0).join();
        }
    }

    public static void parseArgs(String[] args) {
        if (args.length < 4) {
            System.out.println("Illegal input parameters!");
            System.out.println("Format: app.jar objects_type objects_per_second single_object_size_kb lifetime:count,lifetime:count");
            System.out.println("Example: app.jar string|byte 1000 100000 300:10,2000:5,5000:10");
            System.exit(-1);
        } else {
            if (args[0].equals("string")) {
                objectsFactory = new StringMemoryObjectFactory();
            } else if (args[0].equals("byte")) {
                objectsFactory = new ByteMemoryObjectFactory();
            } else {
                throw new IllegalArgumentException("wrong value for parameter objects_type " + args[0]);
            }

            objectsPerSecond = Integer.parseInt(args[1]);
            singleObjectSizeKB = Integer.parseInt(args[2]) * 1024;

            String[] generators = args[3].split(",");
            for (String lifetime : generators) {
                objectGenerators.put(Integer.parseInt(lifetime.split(":")[0]),
                        Integer.parseInt(lifetime.split(":")[1]));
            }
        }
    }
}