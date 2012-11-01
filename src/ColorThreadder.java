
/** Weaves color codes together with threads. */
public class ColorThreadder implements Runnable {
    @Override
    public void run() {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.print("O");
                System.out.print("Y");
            }
        };

        Thread t2 = new Thread(new Blue());
        System.out.print("R");
        t1.start();
        System.out.print("G");
        t2.start();
        System.out.print("I");

        try {
            // Try to join, ignore InterruptedExceptions.
            t1.join();
        } catch (InterruptedException e) { }
        System.out.print("V");

        try {
            // Try to join, ignore InterruptedExceptions.
            t2.join();
        } catch (InterruptedException e) { }
        System.out.print("K");
    }

    /**
     * Ignores {@code args} and calls {@link run()}.
     * @param args Ignored.
     */
    public static void main(String[] args) {
        // Just run it.
        new ColorThreadder().run();
    }

    /**
     * A runnalbe that just prints "B"&mdash; which represents the color blue.
     */
    public static class Blue implements Runnable {
        @Override
        public void run() {
            System.out.print("B");
        }
    }
}
