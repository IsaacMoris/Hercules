package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

public class AudioManager {
    private AudioNode audio;
    private String audioName;
    private static AssetManager assetManager;
    
    public AudioManager(AssetManager assetManager, String name) {
        /*
            AudioData.DataType.Buffer for short sounds e.g: gun shot
            AudioData.DataType.Stream for long sounds e.g: background music
        */
        try {
            audioName = name;
            AudioManager.assetManager = assetManager;
            audio = new AudioNode(assetManager, "Music/soundTracks/" + name);     
            audio.setDirectional(false);
            audio.setPositional(false);
        } catch (Exception e) {
            System.out.printf("Something went wrong with %s file .. Error: %s\n",
                    audioName, e.getMessage());
        }
    }
    
    public void setLooping(boolean loop){
        audio.setLooping(loop);
    }
    public void setVolume(float volume){
        /*at 0: sound is muted, at 2: it is twice as loud, etc.*/
        audio.setVolume(volume);
    }
    
    public void play(){
        audio.play();
    }
    public static void playInstance(String name){
        /* 
            play instance of the sound that can be overlapped
            Must be BUFFER
        */
        try {
            AudioNode myAudio = new AudioNode(assetManager, "Music/soundEffects/" + name,
                    AudioData.DataType.Buffer);
            myAudio.playInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public AudioData.DataType getType(){
        return audio.getType();
    }
    public void stop(){
        audio.stop();
    }
    public void pause(){
        audio.pause();
    }
}
