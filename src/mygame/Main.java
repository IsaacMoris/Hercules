package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import NiftyGui.*;
import GameLevel.*;

public class Main extends SimpleApplication {

    private static int currentLevel = 0;
    private static boolean isResumed = false;
    public static boolean pauseButton = false;
    private static boolean moveToNextLevel;
    private static float soundLevel = 0.1f;
    private static String playerName;
    final private static String LEVEL2Password = "Hades";
    private static boolean goToLevelByPassword = false;
    private static int goToLevel = 0;
    private int waitCounter;

    Menu menu;
    Level level;

    public AppSettings settings;
    BetterInputManager betterInputManager;
    boolean the_level_is_working = false;
    static private AudioManager audioManager;

    public static void main(String[] args) {
        Main app = new Main();
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
                menu.Unload();
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
                    if (level != null) {
                        level.Unload();
                    }
                    level = startLevel(new Level1(), menu);
                    the_level_is_working = true;
                    playMusic("basicGame.ogg");
                } else if (currentLevel == 2) // For going to level 2
                {
                    level = startLevel(new Level2(), menu);
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

                waitCounter = 0;
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
    }

    public static void goToLevelByPassword(int level, String password) {
        if (level == 2 && password.equals(LEVEL2Password)) {
            goToLevelByPassword = true;
            goToLevel = level;

        }
    }

    public static void setCurrentLevel(int level) {
        currentLevel = level;
    }

    public static void moveToNextLevel() {
        moveToNextLevel = true;
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

    private Level startLevel(Level level, Menu menu) {
        menu.Unload();
        level.init(stateManager, this);
        level.Load();
        return level;
    }

    private Menu goToMenu(Menu newMenu) {
        newMenu.init(stateManager, this, newMenu);
        newMenu.Load();
        return newMenu;
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
