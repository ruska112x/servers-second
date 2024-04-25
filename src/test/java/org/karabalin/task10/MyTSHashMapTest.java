package org.karabalin.task10;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

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
    private MyTSHashMap<Integer, String> myTSHashMap;
    private ConcurrentHashMap<Integer, String> cHashMap;

    @Before
    public void setUp() throws Exception {
        myTSHashMap = new MyTSHashMap<>();
        cHashMap = new ConcurrentHashMap<>();
    }

    @Test
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

        System.out.println(endTime - startTime);
        assert (endTime - startTime > 0);
    }

    @Test
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

        System.out.println(endTime - startTime);
        assert (endTime - startTime > 0);
    }
}