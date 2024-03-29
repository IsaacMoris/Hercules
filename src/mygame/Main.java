package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import NiftyGui.*;
import GameLevel.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Main extends SimpleApplication {

    private static int currentLevel = 0;
    private static boolean isResumed = false;
    public static boolean pauseButton = false;
    private static boolean moveToNextLevel;
    private static float soundLevel = 0.1f;
    private static String playerName;
    private static int score;
    final private static String LEVEL2Password = "Hades";
    private static boolean goToLevelByPassword = false;
    private static int goToLevel = 0;
    private int waitCounter;
    private boolean restartGame;

    Menu menu;
    Level level;

    public AppSettings settings;
    BetterInputManager betterInputManager;
    boolean the_level_is_working = false;
    static private AudioManager audioManager;
    public static boolean HercDie = false, HadesDie = false , GOLVL2FROM1=false;

    public static void main(String[] args) {
        Main app = new Main();

        /*AppSettings settings = new AppSettings(true);
        settings.setSettingsDialogImage("Textures/team.jpg");
        app.setSettings(settings);*/
        app.start();
    }

    @Override
    public void simpleInitApp() {
        waitCounter = 0;
        goToLevel = 0;

        settings = new AppSettings(true);
        //settings.setHeight(1280);
        //settings.setWidth(720);
        this.setSettings(settings);
        this.setShowSettings(true);

        //Initialize Screens
        menu = new PreLoadScreen();
        menu.init(stateManager, this, menu);
        menu.Load();

        //Audio Manager
        audioManager = new AudioManager(assetManager, "menuTrack.ogg");
        playMusic("menuTrack.ogg");
        betterInputManager = new BetterInputManager(this.getInputManager());
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (the_level_is_working) {
            level.update(tpf);
        }

        if (moveToNextLevel == true) {
            waitCounter++;
            if (waitCounter == 1) {

                if (currentLevel == 1) {
                    PreLevelScreen.setNextLevel(1);
                }
                if (currentLevel == 2) {
                    PreLevelScreen.setNextLevel(2);
                }

                menu = goToMenu(new PreLevelScreen());
            }
            if (waitCounter == 2) {
                if (currentLevel == 1) {

                    level = loadLevel(new Level1());
                    the_level_is_working = true;
                    playMusic("basicGame.ogg");

                } else if (currentLevel == 2) {

                    level = loadLevel(new Level2());
                    the_level_is_working = true;
                    playMusic("basicGame.ogg");

                }

                moveToNextLevel = false;
                waitCounter = 0;
            }
        }

        if (goToLevelByPassword) {
            {
                if (goToLevel == 2) {
                    currentLevel = 2;
                    moveToNextLevel = true;
                }
                
                goToLevel = 0;
                goToLevelByPassword = false;
            }
        }

        if (pauseButton) {
            level.Unload();
            the_level_is_working = false;
            menu = goToMenu(new PauseMenu());
            pauseButton = false;
            playMusic("menuTrack.ogg");
        }

        if (isResumed) {
            playMusic("basicGame.ogg");
            menu.Unload();
            level.Load();
            the_level_is_working = true;
            isResumed = false;
        }

        if (HercDie) {
            restartGame =true;
             HercDie = false;
            playMusic("GameOver.ogg");
            menu = new PreLoadScreen();
            ((PreLoadScreen) menu).setState(PreLoadScreen.State.loser);
            menu = goToMenu(menu);
            saveScore();

        }

        if (HadesDie) {
            restartGame =true;
            HadesDie = false;
            playMusic("winner.ogg");
            menu = new PreLoadScreen();
            ((PreLoadScreen) menu).setState(PreLoadScreen.State.winner);
            menu = goToMenu(menu);
            saveScore();
            
        }
        if(GOLVL2FROM1)
        {
             clearScene();
             goToLevel(2);
             GOLVL2FROM1=false;
        }
        
        if(restartGame == true)
        {
            if(audioManager.getTimePassed() >= 7)
            {
                playMusic("menuTrack.ogg");
                restartGame =false;
            }
        }
    }

    public static void goToLevelByPassword(int level, String password) {
        if (level == 2 && password.equals(LEVEL2Password)) {
            goToLevelByPassword = true;
            goToLevel = level;

        }
    }

    public static void setPlayerName(String name) {
        playerName = name;
    }

    public static void isResumed(boolean answer) {
        isResumed = answer;
    }

    public static float getSoundLevel() {
        return soundLevel;
    }

    public static void setSoundLevel(float value) {
        soundLevel = value;
        audioManager.setVolume(value);
    }

    private void playMusic(String audioName) {
        audioManager.setTrack(audioName);
        audioManager.setVolume(soundLevel);
        audioManager.play();
    }
    
    public static void setPlayerScore(int value){
        score = value;
    }
    
    private void saveScore(){
        try{
            File file = new File("assets/Score/score.txt");
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter buff = new BufferedWriter(fileWriter);
            String record = playerName;
            for(int i=record.length(); i< 20; i++ )record += ' ';
            record += String.valueOf(score) + "\n";
            buff.write(record); 
            buff.close();
            fileWriter.close();
            score=0;
         }catch (Exception ex){System.out.println(ex);}
    }
    
    
    public static void goToLevel(int index) {
        moveToNextLevel = true;
        currentLevel = index;
    }

    private Level loadLevel(Level level) {

        clearScene();
        level.init(stateManager, this);
        level.Load();
        return level;
    }

    private Menu goToMenu(Menu newMenu) {
        clearScene();
        newMenu.init(stateManager, this, newMenu);
        newMenu.Load();
        return newMenu;
    }

    private void clearScene() {
        this.rootNode.detachAllChildren();
        this.guiNode.detachAllChildren();
        this.viewPort.clearProcessors();
        System.gc();
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
