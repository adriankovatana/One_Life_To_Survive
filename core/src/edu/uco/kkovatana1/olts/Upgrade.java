package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.math.Circle;

public class Upgrade extends Entity{
    protected Globals.UpgradeType type;
    protected Circle hitbox;

    public Upgrade(float x, float y, String filePath, Globals.UpgradeType type) {
        super(x, y, filePath);
        this.type = type;
        hitbox = new Circle(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/2);
    }
}
