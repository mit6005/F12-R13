import java.util.ArrayList;
import java.util.List;

public class Widget extends Thread {
    // 4) Assuming no exception, strings.length() == 0 at the end of main.
    public static List<String> strings = new ArrayList<String>();

    // 3) Assuming no exception, w.count == 1,001 for all w.
    public int count;

    // 2) Accesses to the numbers list are safe because they require the list's
    //    lock.
    public List<Integer> numbers;

    public Widget() {
        // 3) Assuming no exception, w.count == 1,001 for all w.
        count = 0;

        // 2) Accesses to the numbers list are safe because they require the
        //    list's lock.
        numbers = new ArrayList<Integer>();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; ++i) {
            synchronized (this) {
                // 3) Assuming no exception, w.count == 1,001 for all w.
                count++;
                synchronized (numbers) {
                    // 2) Accesses to the numbers list are safe because they
                    //    require the list's lock.
                    numbers.add(i);
                }
                synchronized (Widget.strings) {
                    // 4) Assuming no exception, strings.length() == 0 at the
                    //    end of main.
                    Widget.strings.add("x");
                }
            }
        }
    }

    /**
     * Creates a bunch of widgets and crunches some numbers on them.
     * @param args Ignored.
     */
    public static void main(String[] args) throws InterruptedException {
        // 1) Accesses to the widgets list are safe because the list is confined
        //    to the main thread.

        // Create and start 1,000 widgets and keep them in a list.
        List<Widget> widgets = new ArrayList<Widget>();
        for (int i = 0; i < 1000; ++i) {
            Widget w = new Widget();
            widgets.add(w);
            w.start();
        }

        // Loop through each widget, increment count, add "1,000" to numbers.
        for (Widget w : widgets) {
            synchronized (w) {
                // 3) Assuming no exception, w.count == 1,001 for all w.
                w.count++;

                synchronized (w.numbers) {
                    // 2) Accesses to the numbers list are safe because they
                    //    require the list's lock.
                    w.numbers.add(1000);
                }
            }
            synchronized (Widget.strings) {
                // 4) Assuming no exception, strings.length() == 0 at the end of
                //    main.
                Widget.strings.clear();
            }
        }

        // Wait for all child threads to finish.
        for (Widget w : widgets) {
            w.join();
        }
    }
}
