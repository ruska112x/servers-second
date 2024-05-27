package org.karabalin.task13;

import java.util.Random;

public class Data {
    public int[] get() {
        Random random = new Random();
        int size = random.nextInt(10, 100);
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(0, 9);
        }
        return arr;
    }
}
