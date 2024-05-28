package org.karabalin.task12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Sender {
    private static List<String> readEmails(String file) {
        List<String> emails = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                String email = bufferedReader.readLine();
                emails.add(email);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return emails;
    }

    public static void spam(String message, String sender, String emailsPath) {
        try {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
            List<String> emails = readEmails(emailsPath);
            for (int i = 0; i < emails.size(); ) {
                if (executor.getActiveCount() < executor.getCorePoolSize()) {
                    String email = emails.get(i++);
                    executor.submit(() -> Transport.send(new Message(email, sender, email, message)));
                }
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
