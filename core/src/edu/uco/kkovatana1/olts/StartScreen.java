package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class StartScreen implements Screen {
    private GameMain game;
    private Stage stage;
    private Skin skin;

    private TextButton playBtn;
    private TextButton quitBtn;
    private Entity background;

    public StartScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = GameMain.assetManager.get("ui/uiskin.json", Skin.class);

        //Background
        background = new Entity("background/startscreen.png");
        background.setPosition(0, 0);
        stage.addActor(background);

        //Play button
        playBtn = new TextButton("Play", skin, "default");
        playBtn.getLabel().setFontScale(3.0f * Globals.SCALE);
        playBtn.setWidth(Gdx.graphics.getWidth() * 0.7f);
        playBtn.setHeight(Gdx.graphics.getHeight() * 0.1f);
        playBtn.setPosition(Gdx.graphics.getWidth() * 0.03f, Gdx.graphics.getHeight() * 0.02f);
        playBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.gameScreen);
                return true;
            }
        });
        stage.addActor(playBtn);

        //Quit button
        quitBtn = new TextButton("Quit", skin, "default");
        quitBtn.getLabel().setFontScale(3.0f * Globals.SCALE);
        quitBtn.setWidth(Gdx.graphics.getWidth() * 0.2f);
        quitBtn.setHeight(Gdx.graphics.getHeight() * 0.1f);
        quitBtn.setPosition(Gdx.graphics.getWidth() * 0.77f, Gdx.graphics.getHeight() * 0.02f);
        quitBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        stage.addActor(quitBtn);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}