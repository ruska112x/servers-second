package org.karabalin;

import org.junit.Test;
import org.karabalin.task3.MyThread1;
import org.karabalin.task3.MyThread2;
import org.karabalin.task3.MyThread3;
import org.karabalin.task4.AddingThread;
import org.karabalin.task4.SubtractingThread;
import org.karabalin.task5.FifthTask;
import org.karabalin.task5.FifthThread;
import org.karabalin.task6.SixthTask;
import org.karabalin.task6.SixthThread;
import org.karabalin.task7.PingThread;
import org.karabalin.task7.PongThread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;

public class MainTest {

    @Test
    public void firstTask() {
        System.out.println(Thread.currentThread());
    }

    @Test
    public void secondTask() {
        Thread thread = new Thread(() -> {
            System.out.println("Run method from my thread");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("After sleep");
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End");
    }

    @Test
    public void thirdTask() {
        MyThread1 myThread1 = new MyThread1("1");
        MyThread2 myThread2 = new MyThread2("2");
        MyThread3 myThread3 = new MyThread3("3");

        myThread1.start();
        myThread2.start();
        myThread3.start();

        try {
            myThread1.join();
            myThread2.join();
            myThread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End");
    }

    @Test
    public void forthTask() {
        List<Integer> integers = new ArrayList<>();

        AddingThread addingThread = new AddingThread(integers);
        SubtractingThread subtractingThread = new SubtractingThread(integers);
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

    @Test
    public void fifthTask() {
        List<Integer> integers = new ArrayList<>();
        FifthTask fifthTask = new FifthTask(integers);

        Thread addingThread = new FifthThread(fifthTask, true);
        Thread subtractingThread = new FifthThread(fifthTask, false);
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

    @Test
    public void sixthTask() {
        List<Integer> integers = Collections.synchronizedList(new ArrayList<>());
        SixthTask sixthTask = new SixthTask(integers);
        SixthThread addingThread = new SixthThread(sixthTask, true);
        SixthThread subtractingThread = new SixthThread(sixthTask, false);
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

    @Test
    public void seventhTask() {
        Object lock = new Object();
        AtomicBoolean o = new AtomicBoolean(true);

        PingThread pingThread = new PingThread(lock, o);
        PongThread pongThread = new PongThread(lock, o);

        pingThread.start();
        pongThread.start();
    }
}