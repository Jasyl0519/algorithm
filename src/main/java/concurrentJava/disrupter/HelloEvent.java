package concurrentJava.disrupter;

/**
 * Description:
 * Author: jasonz
 * date: 2021-08-29 下午11:00
 */
public class HelloEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
