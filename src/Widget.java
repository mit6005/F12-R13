import java.util.ArrayList;
import java.util.List;

public class Widget extends Thread {

    public static List<String> strings = new ArrayList<String>();

    public int count;

    public List<Integer> numbers;

    public Widget() {
        count = 0;
        numbers = new ArrayList<Integer>();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; ++i) {
            synchronized (this) {
                count++;
                synchronized (numbers) {
                    numbers.add(i);
                }
                synchronized (Widget.strings) {
                    Widget.strings.add("x");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Widget> widgets = new ArrayList<Widget>();
        for (int i = 0; i < 1000; ++i) {
            Widget w = new Widget();
            widgets.add(w);
            w.start();
        }

        for (Widget w : widgets) {
            synchronized (w) {
                w.count++;
                synchronized (w.numbers) {
                    w.numbers.add(1000);
                }
            }
            synchronized (Widget.strings) {
                Widget.strings.clear();
            }
        }

        for (Widget w : widgets) {
            w.join();
        }
    }
}