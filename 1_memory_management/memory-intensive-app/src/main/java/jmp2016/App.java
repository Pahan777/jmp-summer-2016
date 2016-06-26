package jmp2016;

import jmp2016.generator.MemoryObjectGenerator;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static int objectsPerSecond = 0;
    public static List<Integer> objectsLifetimes = new ArrayList<>();
    public static int singleObjectSizeKB = 0;

    public static void main(String[] args) throws InterruptedException {
        parseArgs(args);

        System.out.println("Starting memory intensive application");
        System.out.println("Application creates " + objectsPerSecond + " per second of size " + singleObjectSizeKB + " bytes");
        System.out.println("With lifetimes " + objectsLifetimes);

        List<MemoryObjectGenerator> generators = new ArrayList<>();

        for (Integer lifetime : objectsLifetimes) {
            MemoryObjectGenerator generator = new MemoryObjectGenerator(lifetime, objectsPerSecond, singleObjectSizeKB);
            generators.add(generator);
            generator.start();
        }

        if (!generators.isEmpty()) {
            generators.get(0).join();
        }
    }

    public static void parseArgs(String[] args) {
        if (args.length < 3) {
            System.out.println("Illegal input parameters!");
            System.out.println("Format: app.jar objects_per_second single_object_size_kb object,life,times");
            System.out.println("Example: app.jar 1000 100000 300,2000,5000");
            System.exit(-1);
        } else {
            objectsPerSecond = Integer.parseInt(args[0]);
            singleObjectSizeKB = Integer.parseInt(args[1]) * 1024;

            String[] lifetimes = args[2].split(",");
            for (String lifetime : lifetimes) {
                objectsLifetimes.add(Integer.parseInt(lifetime));
            }
        }
    }
}