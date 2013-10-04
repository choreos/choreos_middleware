package org.ow2.choreos.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;

import org.eclipse.jetty.util.URIUtil;

public class Alarm {

    private static final String SONGS_FOLDER = System.getProperty("user.home") + "/Music";

    public void play() {
        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".mp3");
            }
        };
        File folder = new File(SONGS_FOLDER);
        File[] songs = folder.listFiles(filter);
        int numberOfSongs = songs.length;
        Random random = new Random();
        int songIndex = random.nextInt(numberOfSongs);
        File song = songs[songIndex];
        String songFileName = URIUtil.encodePath(song.getAbsolutePath());
        String commandStr = "vlc file://" + songFileName;
        try {
            OSCommand command = new OSCommand(commandStr);
            command.execute();
        } catch (CommandLineException e) {
            System.out.println("Alarm!");
        }
    }

    public static void main(String[] args) {
        Alarm a = new Alarm();
        a.play();
    }
}
