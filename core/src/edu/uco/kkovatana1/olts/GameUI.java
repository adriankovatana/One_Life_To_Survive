package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.text.DecimalFormat;

public class GameUI extends Group{
    private GameScreen gameScreen;

    protected Touchpad movementpad;
    protected Touchpad shootingpad;
    private Skin touchpadSkin;
    private Touchpad.TouchpadStyle touchpadStyle;
    protected UpgradeIcon speedIcon;
    protected UpgradeIcon firerateIcon;
    protected Text score;
    protected Entity settingsIcon;

    protected Group pausedGroup;
    protected Entity pauseOverlay;
    protected Entity resumeBtn;
    protected Entity quitBtn;

    protected Group gameOverGroup;
    protected Entity gameoverOverlay;
    protected Text finalScore;
    protected Text time;
    protected Text accuracy;
    protected Text kills;

    public GameUI(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;

        touchpadSkin = new Skin();
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadSkin.add("touchBackground", GameMain.assetManager.get("ui/touchBackground.png", Texture.class));
        touchpadSkin.add("touchKnob", GameMain.assetManager.get("ui/touchKnob.png", Texture.class));
        touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
        touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");

        //Movement Touchpad
        movementpad = new Touchpad(10 * Globals.SCALE, touchpadStyle);
        movementpad.setBounds(Gdx.graphics.getWidth() * 0.01f, Gdx.graphics.getHeight() * 0.01f,
                400 * Globals.SCALE, 400 * Globals.SCALE);
        addActor(movementpad);

        //Shooting Touchpad
        shootingpad = new Touchpad(10 * Globals.SCALE, touchpadStyle);
        shootingpad.setBounds(Gdx.graphics.getWidth() * 0.791f, Gdx.graphics.getHeight() * 0.01f,
                400 * Globals.SCALE, 400 * Globals.SCALE);
        addActor(shootingpad);

        //Movement Speed
        speedIcon = new UpgradeIcon(0.01f*Gdx.graphics.getWidth(), 0.9f*Gdx.graphics.getHeight(), "ui/speedIcon.png");
        addActor(speedIcon);

        //Firerate
        firerateIcon = new UpgradeIcon(0.117f*Gdx.graphics.getWidth(), 0.9f*Gdx.graphics.getHeight(), "ui/firerateIcon.png");
        addActor(firerateIcon);

        //Score
        score = new Text(0.5f*Gdx.graphics.getWidth(), 0.9f*Gdx.graphics.getHeight(), ""+0);
        score.bitmapFont.getData().setScale(5 * Globals.SCALE);
        addActor(score);

        //Settings
        settingsIcon = new Entity(0.94f*Gdx.graphics.getWidth(), 0.9f*Gdx.graphics.getHeight(), "ui/settings.png");
        settingsIcon.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.pauseSession(true);
                pausedGroup.setVisible(true);
                return true;
            }
        });
        addActor(settingsIcon);

        //Paused Group
        pausedGroup = new Group();
        addActor(pausedGroup);

        pauseOverlay = new Entity(0,0,"ui/pauseoverlay.png");
        pausedGroup.addActor(pauseOverlay);

        resumeBtn = new Entity(0.3f*Gdx.graphics.getWidth(), 0.5f*Gdx.graphics.getHeight(),"ui/resumebutton.png");
        resumeBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.pauseSession(false);
                pausedGroup.setVisible(false);
                return true;
            }
        });
        pausedGroup.addActor(resumeBtn);

        quitBtn = new Entity(0.3f*Gdx.graphics.getWidth(), 0.2f*Gdx.graphics.getHeight(),"ui/quitbutton.png");
        quitBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.quitSession();
                return true;
            }
        });
        pausedGroup.addActor(quitBtn);

        pausedGroup.setVisible(false);

        //Game Over
        gameOverGroup = new Group();
        addActor(gameOverGroup);

        gameoverOverlay = new Entity(0,0,"ui/gameoveroverlay.png");
        gameoverOverlay.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.quitSession();
                return true;
            }
        });
        gameOverGroup.addActor(gameoverOverlay);

        finalScore = new Text(0.55f*Gdx.graphics.getWidth(), 0.4f*Gdx.graphics.getHeight(), "0");
        finalScore.bitmapFont.setColor(Color.YELLOW);
        finalScore.bitmapFont.getData().setScale(5 * Globals.SCALE);
        gameOverGroup.addActor(finalScore);

        time = new Text(0.55f*Gdx.graphics.getWidth(), 0.3f*Gdx.graphics.getHeight(), "0");
        time.bitmapFont.setColor(Color.YELLOW);
        time.bitmapFont.getData().setScale(5 * Globals.SCALE);
        gameOverGroup.addActor(time);

        accuracy = new Text(0.55f*Gdx.graphics.getWidth(), 0.1f*Gdx.graphics.getHeight(),"0");
        accuracy.bitmapFont.setColor(Color.YELLOW);
        accuracy.bitmapFont.getData().setScale(5 * Globals.SCALE);
        gameOverGroup.addActor(accuracy);

        kills = new Text(0.55f*Gdx.graphics.getWidth(), 0.2f*Gdx.graphics.getHeight(), "0");
        kills.bitmapFont.setColor(Color.YELLOW);
        kills.bitmapFont.getData().setScale(5 * Globals.SCALE);
        gameOverGroup.addActor(kills);

        gameOverGroup.setVisible(false);
    }

    protected void gameOver(){
        finalScore.text = ""+gameScreen.gameSession.score;
        int seconds = gameScreen.gameSession.elapsedTime % 60;
        int minutes = gameScreen.gameSession.elapsedTime / 60;
        Gdx.app.log("Time", minutes + ":" + seconds);
        if(seconds > 9)
            time.text = minutes + ":" + seconds;
        else
            time.text = minutes + ":0" + seconds;
        accuracy.text = String.format("%.2f",gameScreen.gameSession.getAccuracy()*100) + "%";
        kills.text = "" + gameScreen.gameSession.kills;
        gameOverGroup.setVisible(true);
    }
}
