package apkhelper.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * if UnsupportedAudioFile is thrown, make sure to convert to .wav 
 * using http://www.online-convert.com/ or use an application
 * 
 */


// Call loop method to loop an audio (Like background music), and call stop
// to break the loop

// Call start method to simply start an audio, just once. (Jump sound fx, etc..)

// Call isActive to check if the clip is currently running

public final class SoundPlayer {
    
    public static final float VOLUME_MAX = 6.0206f; // the max volume supported
    public static final float VOLUME_LOW = -30f;    // the lowest human sound
    public static final float VOLUME_MUTE = -80f;   // no sound
     
    private Clip clip;  // this will hold the current sound
    
    public SoundPlayer (String soundName) {
        
        if(!soundName.startsWith("audio/")){
            // to make sure that the file is in the audio package
            soundName = ("audio/")+soundName;
        }
        if(!soundName.endsWith(".wav")){
            // this class only plays .wav files
            System.out.println("Make sure the sound file is a .wav !");
            return;
        }
        
        try {
            InputStream audioSrc = ClassLoader.getSystemClassLoader()
                        .getResourceAsStream(soundName);    // get the audio source file
            InputStream buffered = new BufferedInputStream(audioSrc);   // put the audio in a buffer
            AudioInputStream ais =
                        AudioSystem.getAudioInputStream(buffered);  // put the buffered in a stream to play
            
            clip = AudioSystem.getClip();   // get the sound from the stream
            clip.open(ais); // open the sound
            changeVolume(VOLUME_MAX);   // make the sound the highest possible volume
  
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);  // if the file is corrupt, not found, or something else went wrong
        }
    }
 
    // play the sound
    public void play() {
        final Clip clip2 = this.clip;
        try {
            if (clip2 != null && !clip2.isActive()) {
                // if the clip is ready to play
                new Thread() {
                    // play it in a thread to let the computer decide when to play the sound
                    @Override
                    public void run() {
                        synchronized (clip2) {
                            // restart the clip from 0 seconds
                            clip2.stop();
                            clip2.setFramePosition(0);
                            clip2.start();
                        }
                    }
                }.start();  // start the thread
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     
    // stop the current sound
    public void stop(){
        if(clip == null) return;
        clip.stop();
    }
    
    // play the sound over and over infinity times
    public void loop() {
        final Clip clip2 = this.clip;
        try {
            if (clip2 != null && !clip2.isActive()) {
                new Thread() {
                    @Override
                    public void run() {
                        synchronized (clip2) {
                            clip2.stop();
                            clip2.setFramePosition(0);
                            clip2.loop(Clip.LOOP_CONTINUOUSLY); // this tells us to loop infinite times
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // if the sound is still playing
    public boolean isActive(){
        return clip.isActive();

    }
    
    // play the sound, but start at low volume and keep increasing it
    public void fadeIn(){
        final Clip clip2 = this.clip;
        try {
            if (clip2 != null && !clip2.isActive()) {
                new Thread() {
                    @Override
                    public void run() {
                        if(!clip2.isActive())
                            clip2.start();
                        for(float x = VOLUME_LOW; x < VOLUME_MAX; x = x + 0.1f){
                            changeVolume(x);
                            System.out.println(x);
                            try{
                                // The larger the sleep the longer it takes for
                                // the track to fade in
                                Thread.sleep(60);
                            }catch(Exception e){
                                System.out.println(e.toString());
                            }
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // "fades out" the audio then stops it
    public void fadeOut(){
        final Clip clip2 = this.clip;
        try {
            if (clip2 != null && !clip2.isActive()) {
                new Thread() {
                    @Override
                    public void run() {
                        if(!clip2.isActive())
                            clip2.start();
                        for(float x = VOLUME_MAX; x > VOLUME_MUTE; x = x - 0.1f){
                            changeVolume(x);
                            System.out.println(x);
                            try{
                                // The larger the sleep the longer the fade out
                                Thread.sleep(5);
                            }catch(Exception e){
                                System.out.println(e.toString());
                            }
                        }
                        clip2.stop();
                    }
                }.start();

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Changes the volume of an audio
    public void changeVolume(float f){
        if(f > VOLUME_MAX){
            f = VOLUME_MAX;
        }
        
        FloatControl gainControl = 
            (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(f); // Reduce volume by 10 decibels.
    }
    
    public double getTotalTime(){
        // returns length of track in 'minute.seconds' format
        return ((double)clip.getMicrosecondLength() * (1 * Math.pow(10,-6))) / 60; 
    }
    
    public double getTotalSeconds(){
        // returns length of track in seconds
        return ((double)clip.getMicrosecondLength() * (1 * Math.pow(10,-6)));
    }
}