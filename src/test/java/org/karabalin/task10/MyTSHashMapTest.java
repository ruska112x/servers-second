package org.karabalin.task10;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MapThread extends Thread {
    private final Map<Integer, String> map;
    private final int a;
    private final int b;

    public MapThread(Map<Integer, String> map, int a, int b) {
        this.map = map;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        for (int i = a; i < b; ++i) {
            // System.out.println("Puting in thread:" + this.getName());
            map.put(i, String.format("num%d", i));
        }
    }
}

public class MyTSHashMapTest {
    private static MyTSHashMap<Integer, String> myTSHashMap;
    private static ConcurrentHashMap<Integer, String> cHashMap;

    @BeforeAll
    public static void setUp() throws Exception {
        myTSHashMap = new MyTSHashMap<>();
        cHashMap = new ConcurrentHashMap<>();
    }

    @RepeatedTest(20)
    public void testMyTSHashMap() {
        final long startTime = System.currentTimeMillis();

        int size = 8;
        int batchSize = 1000;
        MapThread[] mapThread = new MapThread[size];
        for (int i = 0; i < size; i++) {
            mapThread[i] = new MapThread(myTSHashMap, i * batchSize, (i + 1) * batchSize);
        }
        for (int i = 0; i < size; i++) {
            mapThread[i].start();
        }
        for (int i = 0; i < size; i++) {
            try {
                mapThread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        final long endTime = System.currentTimeMillis();

        System.out.println("MyTSHashMap: e=" + endTime + ", s=" + startTime);
        assertTrue(endTime - startTime > 0l);
    }

    @RepeatedTest(20)
    public void testConcurrentHashMap() {
        final long startTime = System.currentTimeMillis();

        int size = 8;
        int batchSize = 1000;
        MapThread[] mapThread = new MapThread[size];
        for (int i = 0; i < size; i++) {
            mapThread[i] = new MapThread(cHashMap, i * batchSize, (i + 1) * batchSize);
        }
        for (int i = 0; i < size; i++) {
            mapThread[i].start();
        }
        for (int i = 0; i < size; i++) {
            try {
                mapThread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        final long endTime = System.currentTimeMillis();

        System.out.println("Concurrent: e=" + endTime + ", s=" + startTime);
        assertTrue(endTime - startTime > 0l);
    }
}