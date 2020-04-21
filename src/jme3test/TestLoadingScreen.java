package jme3test;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.niftygui.NiftyJmeDisplay;
import static com.jme3.niftygui.NiftyJmeDisplay.newNiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Controller;
import de.lessvoid.nifty.controls.Parameters;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import java.util.ArrayList;
import java.util.List;
import mygame.level1_scene;

/**
 * This is the TestLoadingScreen Class of your Game. You should only do
 * initialization here. Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class TestLoadingScreen extends SimpleApplication implements
        ScreenController, Controller {

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private Element progressBarElement;
    private TerrainQuad terrain;
    private Material mat_terrain;
    private float frameCount = 0;
    private boolean load = false;
    private TextRenderer textRenderer;

    public static void main(String[] args) {
       TestLoadingScreen app = new TestLoadingScreen();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        niftyDisplay = newNiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/nifty_loading.xml", "start", this);

        guiViewPort.addProcessor(niftyDisplay);
    }

    @Override
    public void simpleUpdate(float tpf) {

        if (load) { //loading is done over many frames
            if (frameCount == 1) {
                Element element = nifty.getScreen("loadlevel").findElementById(
                        "loadingtext");
                textRenderer = element.getRenderer(TextRenderer.class);

                mat_terrain = new Material(assetManager,
                        "Common/MatDefs/Terrain/Terrain.j3md");
                mat_terrain.setTexture("Alpha", assetManager.loadTexture(
                        "Textures/Terrain/splat/alphamap.png"));
                setProgress(0.2f, "Loading grass");

            } else if (frameCount == 2) {
                Texture grass = assetManager.loadTexture(
                        "Textures/Terrain/splat/grass.jpg");
                grass.setWrap(WrapMode.Repeat);
                mat_terrain.setTexture("Tex1", grass);
                mat_terrain.setFloat("Tex1Scale", 64f);
                setProgress(0.4f, "Loading dirt");

            } else if (frameCount == 3) {
                Texture dirt = assetManager.loadTexture(
                        "Textures/Terrain/splat/dirt.jpg");

                dirt.setWrap(WrapMode.Repeat);
                mat_terrain.setTexture("Tex2", dirt);
                mat_terrain.setFloat("Tex2Scale", 32f);
                setProgress(0.5f, "Loading rocks");

            } else if (frameCount == 4) {
                Texture rock = assetManager.loadTexture(
                        "Textures/Terrain/splat/road.jpg");

                rock.setWrap(WrapMode.Repeat);

                mat_terrain.setTexture("Tex3", rock);
                mat_terrain.setFloat("Tex3Scale", 128f);
                setProgress(0.6f, "Creating terrain");

            } else if (frameCount == 5) {
                AbstractHeightMap heightmap = null;
                Texture heightMapImage = assetManager.loadTexture(
                        "Textures/Terrain/splat/mountains512.png");
                heightmap = new ImageBasedHeightMap(heightMapImage.getImage());

                heightmap.load();
                terrain = new TerrainQuad("my terrain", 65, 513, heightmap.
                        getHeightMap());
                setProgress(0.8f, "Positioning terrain");

            } else if (frameCount == 6) {
                terrain.setMaterial(mat_terrain);

                terrain.setLocalTranslation(0, -100, 0);
                terrain.setLocalScale(2f, 1f, 2f);
                rootNode.attachChild(terrain);
                setProgress(0.9f, "Loading cameras");

            } else if (frameCount == 7) {
                List<Camera> cameras = new ArrayList<>();
                cameras.add(getCamera());
                TerrainLodControl control = new TerrainLodControl(terrain,
                        cameras);
                terrain.addControl(control);
                setProgress(1f, "Loading complete");

            } else if (frameCount == 8) {
                nifty.gotoScreen("end");
                nifty.exit();
                guiViewPort.removeProcessor(niftyDisplay);
                flyCam.setEnabled(true);
                flyCam.setMoveSpeed(50);
            }

            frameCount++;
        }
    }

    public void setProgress(final float progress, String loadingText) {
        final int MIN_WIDTH = 32;
        int pixelWidth = (int) (MIN_WIDTH + (progressBarElement.getParent().
                getWidth() - MIN_WIDTH) * progress);
        progressBarElement.setConstraintWidth(new SizeValue(pixelWidth + "px"));
        progressBarElement.getParent().layoutElements();

        textRenderer.setText(loadingText);
    }

    public void showLoadingMenu() {
        nifty.gotoScreen("loadlevel");
        load = true;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        progressBarElement = nifty.getScreen("loadlevel").findElementById(
                "progressbar");
    }

    // methods for Controller
    @Override
    public boolean inputEvent(final NiftyInputEvent inputEvent) {
        return false;
    }

    @Override
    public void onFocus(boolean getFocus) {
    }

    @Override
    public void bind(Nifty nifty, Screen screen, Element elmnt,
            Parameters prmtrs) {
        progressBarElement = elmnt.findElementById("progressbar");
    }

    @Override
    public void init(Parameters prmtrs) {
    }

}