package org.ow2.choreos.tracker;

import org.ow2.choreos.utils.CommandLineException;
import org.ow2.choreos.utils.OSCommand;

public class Alarm {

    private static final String COMMAND = "vlc /home/leonardo/sowhat.mp3";

    public void play() {
        OSCommand command = new OSCommand(COMMAND);
        try {
            command.execute();
        } catch (CommandLineException e) {
            System.out.println("SO FUCKING WHAT?!");
        }
    }

    public static void main(String[] args) throws Exception {
        Alarm alarm = new Alarm();
        alarm.play();
    }

}
