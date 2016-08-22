package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LoadingScreen implements Screen {
    private GameMain game;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite background;
    private ShapeRenderer shapeRenderer;

    public LoadingScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        texture = new Texture(Gdx.files.internal("background/loadingscreen.png"));
        background = new Sprite(texture);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.1f,
                Gdx.graphics.getWidth() * 0.9f, Gdx.graphics.getHeight()* 0.05f);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(Gdx.graphics.getWidth() * 0.055f, Gdx.graphics.getHeight() * 0.105f,
                (Gdx.graphics.getWidth() * 0.89f) * game.assetManager.getProgress(),
                Gdx.graphics.getHeight()* 0.04f);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        Globals.SCALE = (width/1920.0f);
        background.setSize(width,height);
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
        texture.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}