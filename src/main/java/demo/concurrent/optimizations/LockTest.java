package demo.concurrent.optimizations;

/**
 * Java concurency optimisations benchmark based on article:
 * http://www.infoq.com/articles/java-threading-optimizations-p1
 * http://www.infoq.com/articles/java-threading-optimizations-p2
 */
public class LockTest {
    private static final int MAX = 20000000; // 20 million

    public static void main(String[] args) throws InterruptedException {
        // warm up the method cache
        for (int i = 0; i < MAX; i++) {
            concatBuffer("Josh", "James", "Duke");
            concatBuilder("Josh", "James", "Duke");
        }

        System.gc();
        Thread.sleep(1000);

        System.out.println("Starting test");
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            concatBuffer("Josh", "James", "Duke");
        }
        long bufferCost = System.currentTimeMillis() - start;
        System.out.println("StringBuffer: " + bufferCost + " ms.");

        System.gc();
        Thread.sleep(1000);

        start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            concatBuilder("Josh", "James", "Duke");
        }
        long builderCost = System.currentTimeMillis() - start;
        System.out.println("StringBuilder: " + builderCost + " ms.");
        System.out.println("Thread safety overhead of StringBuffer: "
                + ((bufferCost * 10000 / (builderCost * 100)) - 100) + "%\n");

    }

    public static String concatBuffer(String s1, String s2, String s3) {
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        sb.append(s3);
        return sb.toString();
    }

    public static String concatBuilder(String s1, String s2, String s3) {
        StringBuilder sb = new StringBuilder();
        sb.append(s1);
        sb.append(s2);
        sb.append(s3);
        return sb.toString();
    }

}