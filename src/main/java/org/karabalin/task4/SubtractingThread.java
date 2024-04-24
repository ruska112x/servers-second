package org.karabalin.task4;

import java.util.List;
import java.util.Random;

public class SubtractingThread extends Thread {
    private final List<Integer> integers;
    private final Random random = new Random();


    public SubtractingThread(List<Integer> integers) {
        this.integers = integers;
    }

    @Override
    public void run() {
        for (int i = 10000; i > 0; i--) {
            synchronized (integers) {
                if (integers.isEmpty()) {
                    try {
                        integers.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                integers.remove(random.nextInt(integers.size()));
                System.out.println("D" + i);
            }
        }
    }
}
