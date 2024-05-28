package org.karabalin.task13;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            ExecutorService writers = Executors.newFixedThreadPool(numOfWriters);
            ExecutorService readers = Executors.newFixedThreadPool(numOfReaders);
            for (int i = 0; i < numOfWriters; i++) {
                writers.execute(new DataWriter(queue));
            }
            for (int i = 0; i < numOfReaders; i++) {
                readers.execute(new DataReader(queue));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
