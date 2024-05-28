package org.karabalin.task13;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task13 {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("task13").build();
        parser.addArgument("-w")
                .metavar("w")
                .type(Integer.class);
        parser.addArgument("-r")
                .metavar("r")
                .type(Integer.class);
        parser.addArgument("-c")
                .metavar("c")
                .type(Integer.class)
                .setDefault(8);
        try {
            Namespace res = parser.parseArgs(args);
            int numOfWriters = res.get("w");
            int numOfReaders = res.get("r");
            int capacity = res.get("c");
            BlockingQueue<Data> queue = new ArrayBlockingQueue<>(capacity);
            Thread[] writers = new Thread[numOfWriters];
            Thread[] readers = new Thread[numOfReaders];
            for (int i = 0; i < numOfWriters; i++) {
                writers[i] = new Thread(new Writer(queue));
                writers[i].start();
            }
            for (int i = 0; i < numOfReaders; i++) {
                readers[i] = new Thread(new Reader(queue));
                readers[i].start();
            }
//            for (int i = 0; i < numOfWriters; i++) {
//                writers[i].join();
//            }
//            for (int i = 0; i < numOfReaders; i++) {
//                readers[i].join();
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
