package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Bullet extends Entity{
    protected float movementSpeed;
    protected float damage;
    protected Vector2 vector;
    protected Circle hitbox;

    public Bullet(float x, float y, float damage, float dx, float dy) {
        super(x, y, "projectile/bullet.png");
        setPosition(getX() - getWidth() / 2, getY() - getHeight() / 2);
        movementSpeed = 20.0f;
        this.damage = damage;
        vector = new Vector2(dx, dy);
        hitbox = new Circle(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/2);
        this.addAction(updateAction());
    }

    protected Action updateAction() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                setX(getX() + (vector.x * movementSpeed * Globals.SCALE));
                setY(getY() + (vector.y * movementSpeed * Globals.SCALE));
                hitbox.setPosition(getX()+getWidth()/2, getY()+getHeight()/2);
                return false;
            }
        };
    }
}
