package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {
    protected BitmapFont bitmapFont;
    protected String text;

    public Text(float x, float y, String text){
        setPosition(x, y);
        bitmapFont = new BitmapFont();
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        bitmapFont.draw(batch, text, getX()-50, getY()+bitmapFont.getLineHeight());
    }
}
