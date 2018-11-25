package uet.oop.bomberman;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    // class dung de add cac file am thanh duoi .WAV
    // link: https://www.youtube.com/watch?v=QVrxiJyLTqU&index=2&list=PLyiVH3XjnQ4-sOwz7qKJKwONoDPo1CUM4
    public Sound() {
    }
    
    public static void playSound(File sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
