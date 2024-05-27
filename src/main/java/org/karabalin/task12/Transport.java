package org.karabalin.task12;

import java.io.FileWriter;
import java.io.IOException;

public class Transport {
    public static void send(Message message) {
        try (FileWriter fileWriter = new FileWriter(message.getEmailAddress() + ".m")) {
            fileWriter.write("From:" + message.getSender() + "\n");
            fileWriter.write(message.getBody());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
