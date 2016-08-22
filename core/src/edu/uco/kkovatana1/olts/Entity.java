package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Entity extends Actor {

    protected Sprite sprite;

    public Entity(String filePath) {
        sprite = new Sprite(GameMain.assetManager.get(filePath, Texture.class));
        resize(Globals.SCALE);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
        setPosition(0, 0);
        sprite.setPosition(getX(), getY());
    }

    public Entity(float x, float y, String filePath) {
        sprite = new Sprite(GameMain.assetManager.get(filePath, Texture.class));
        resize(Globals.SCALE);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
        setPosition(x, y);
        sprite.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(),getY());
        sprite.draw(batch, parentAlpha);
    }

    protected void resize(float scale){
        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
    }
}
