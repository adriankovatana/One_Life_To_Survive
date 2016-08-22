package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameMain extends Game {
    protected LoadingScreen loadingScreen;
    protected StartScreen startScreen;
    protected GameScreen gameScreen;

    public static AssetManager assetManager;
    private boolean loaded;

    @Override
    public void create() {
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);

        assetManager = new AssetManager();
        loadAssests();
        loaded = false;
    }

    @Override
    public void render() {
        super.render();
        if (!loaded && assetManager.update())
            init();
    }

    @Override
    public void dispose() {
        startScreen.dispose();
        assetManager.clear();
        loadingScreen.dispose();
    }

    private void init() {
        startScreen = new StartScreen(this);
        gameScreen = new GameScreen(this);

        loaded = true;
        setScreen(startScreen);
    }

    private void loadAssests() {
        //Backgrounds
        assetManager.load("background/startscreen.png", Texture.class);
        assetManager.load("background/concrete.png", Texture.class);

        //UI
        assetManager.load("ui/uiskin.json", Skin.class);
        assetManager.load("ui/pauseoverlay.png", Texture.class);
        assetManager.load("ui/speedIcon.png", Texture.class);
        assetManager.load("ui/firerateIcon.png", Texture.class);
        assetManager.load("ui/settings.png", Texture.class);
        assetManager.load("ui/gameoveroverlay.png", Texture.class);
        assetManager.load("ui/pauseoverlay.png", Texture.class);
        assetManager.load("ui/resumebutton.png", Texture.class);
        assetManager.load("ui/quitbutton.png", Texture.class);

        //TouchPadTest
        assetManager.load("ui/touchBackground.png", Texture.class);
        assetManager.load("ui/touchKnob.png", Texture.class);

        //Characters
        assetManager.load("character/survivor.png", Texture.class);
        assetManager.load("character/zombie.png", Texture.class);

        //Projectile
        assetManager.load("projectile/bullet.png", Texture.class);

        //Upgrades
        assetManager.load("upgrade/speed.png", Texture.class);
        assetManager.load("upgrade/firerate.png", Texture.class);
    }
}