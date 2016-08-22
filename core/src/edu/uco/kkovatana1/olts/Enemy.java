package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Enemy extends Entity{
    protected float movementSpeed;
    protected Vector2 target;
    protected Vector2 dvector;
    protected Circle hitbox;

    public Enemy(float x, float y, Vector2 target){
        super(x, y, "character/zombie.png");
        movementSpeed = 1.0f;
        this.target = target;
        dvector = new Vector2(0,0);
        hitbox = new Circle(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/3);
        this.addAction(updateAction());
    }

    protected Action updateAction() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                homingOnTarget();
                setX(getX() + dvector.x);
                setY(getY() + dvector.y);
                hitbox.setPosition(getX()+getWidth()/2, getY()+getHeight()/2);
                return false;
            }
        };
    }

    protected void homingOnTarget() {
        float tempX = target.x - getX()-getWidth()/2;
        float tempY = target.y - getY()-getHeight()/2;
        float rads = MathUtils.atan2(tempY, tempX);
        dvector.x = MathUtils.cos(rads)*movementSpeed*Globals.SCALE;
        dvector.y = MathUtils.sin(rads)*movementSpeed*Globals.SCALE;
        sprite.setRotation(rads*MathUtils.radiansToDegrees);
    }
}
