package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Darth Kova on 1/21/2016.
 */
public class UpgradeIcon extends Entity {
    protected int level;
    protected BitmapFont levelText;

    public UpgradeIcon(float x, float y, String filePath) {
        super(x, y, filePath);
        level = 1;
        levelText = new BitmapFont();
        levelText.setColor(Color.WHITE);
        levelText.getData().setScale(5*Globals.SCALE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        levelText.draw(batch,""+level,getX()+getWidth()+10,getY()+levelText.getLineHeight());
    }

    protected void upgrade(){
        level++;
    }
}
