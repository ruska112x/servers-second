package org.karabalin.task5;

import java.util.List;
import java.util.Random;

public class FifthTask {

    private final List<Integer> integers;
    private final Random random = new Random();

    public FifthTask(List<Integer> integers) {
        this.integers = integers;
    }

    public synchronized void adding() {
        integers.add(random.nextInt());
    }

    public synchronized void subtracting() {
        if (!integers.isEmpty()) {
            integers.remove(random.nextInt(integers.size()));
        }
    }

    public boolean isEmpty() {
        return integers.isEmpty();
    }
}
