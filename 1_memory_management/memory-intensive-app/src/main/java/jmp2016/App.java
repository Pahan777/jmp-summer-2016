package jmp2016;

import jmp2016.generator.HugeObjectsGenerator;

public class App {

    public static final int YOUNG_OBJECTS_LIFETIME = 100;
    public static final int MEDIUM_OBJECTS_LIFETIME = 500;
    public static final int HUGE_OBJECTS_LIFETIME = 5000;

    public static int objectsPerSecond = 0;

    public static void main(String[] args) throws InterruptedException {
        parseArgs(args);

        System.out.println("Starting memory intensive application");
        System.out.println("        Objects per second = " + objectsPerSecond);

        System.out.println("Application creates " + objectsPerSecond + " per second");
        System.out.println("With lifetimes " + YOUNG_OBJECTS_LIFETIME + ", " + MEDIUM_OBJECTS_LIFETIME + ", " + HUGE_OBJECTS_LIFETIME);

        hugeObjectsYoungMediumOldHeavyLoad(objectsPerSecond);
    }

    public static void hugeObjectsYoungMediumOldHeavyLoad(int objectsPerSecond) throws InterruptedException {
        HugeObjectsGenerator youngGenerator = new HugeObjectsGenerator(YOUNG_OBJECTS_LIFETIME, objectsPerSecond);
        HugeObjectsGenerator mediumGenerator = new HugeObjectsGenerator(MEDIUM_OBJECTS_LIFETIME, objectsPerSecond);
        HugeObjectsGenerator oldGenerator = new HugeObjectsGenerator(HUGE_OBJECTS_LIFETIME, objectsPerSecond);

        youngGenerator.start();
        mediumGenerator.start();
        oldGenerator.start();

        youngGenerator.join();
    }

    public static void parseArgs(String[] args) {
        if (args.length < 1) {
            System.out.println("Illegal input parameters!");
            System.out.println("Example: app.jar objects_per_second");
            System.exit(-1);
        } else {
            objectsPerSecond = Integer.parseInt(args[0]);
        }
    }
}