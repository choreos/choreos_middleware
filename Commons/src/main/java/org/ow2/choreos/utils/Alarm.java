package org.ow2.choreos.utils;

import java.io.File;
import java.util.Random;

public class Alarm {
    
    private static final String SONGS_FOLDER = System.getProperty("user.home") + "/Music"; 

    public void play() {
        File folder = new File(SONGS_FOLDER);
        File[] songs = folder.listFiles();
        int numberOfSongs = songs.length;
        Random random = new Random();
        int songIndex = random.nextInt(numberOfSongs);
        File song = songs[songIndex];
        String commandStr = "vlc file://" + song.getAbsolutePath().replaceAll(" ", "%20");
        OSCommand command = new OSCommand(commandStr);
        try {
            command.execute();
        } catch (CommandLineException e) {
            System.out.println("Alarm!");
        }
    }
    
}
