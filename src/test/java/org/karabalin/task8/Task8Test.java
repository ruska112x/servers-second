package org.karabalin.task8;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AddingThreadWithLock extends Thread {

    private final Lock lock;

    private final List<Integer> integers;
    private final Random random = new Random();

    private final Condition condition;

    public AddingThreadWithLock(Lock lock, Condition condition, List<Integer> integers) {
        this.lock = lock;
        this.condition = condition;
        this.integers = integers;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; ++i) {
            lock.lock();
            try {
                integers.add(random.nextInt());
                System.out.println("A" + i);
                condition.signal();
            } finally {
                lock.unlock();
            }
        }
    }
}

class SubtractingThreadWithLock extends Thread {

    private final Lock lock;

    private final Condition condition;
    private final List<Integer> integers;
    private final Random random = new Random();


    public SubtractingThreadWithLock(Lock lock, Condition condition, List<Integer> integers) {
        this.lock = lock;
        this.condition = condition;
        this.integers = integers;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; ) {
            lock.lock();
            try {
                if (integers.isEmpty()) {
                    condition.await();
                }
                int index = random.nextInt(integers.size());
                integers.remove(index);
                System.out.println("D" + i);
                ++i;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}

public class Task8Test {
    @Test
    public void eighthTask() {
        List<Integer> integers = new ArrayList<>();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AddingThreadWithLock addingThread = new AddingThreadWithLock(lock, condition, integers);
        SubtractingThreadWithLock subtractingThread = new SubtractingThreadWithLock(lock, condition, integers);
        addingThread.start();
        subtractingThread.start();
        try {
            addingThread.join();
            subtractingThread.join();
            assertEquals(0, integers.size());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}