package org.karabalin.task6;

import java.util.List;
import java.util.Random;

public class SixthTask {
    private final List<Integer> integers;
    private final Random random = new Random();

    public SixthTask(List<Integer> integers) {
        this.integers = integers;
    }

    public void adding() {
        integers.add(random.nextInt());
    }

    public void subtracting() {
        if (!integers.isEmpty()) {
            integers.remove(random.nextInt(integers.size()));
        }
    }

    public boolean isEmpty() {
        return integers.isEmpty();
    }
}
