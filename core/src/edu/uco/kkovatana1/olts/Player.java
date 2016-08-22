package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;

public class Player extends Entity {
    protected float movementSpeed;
    protected float attackSpeed;
    protected float damage;
    protected Globals.EntityState state;
    protected Globals.Direction direction;
    protected Vector2 directionVector;
    protected float attackCooldown;
    protected boolean onCooldown;
    protected Vector2 position;
    protected Circle hitbox;

    public Player() {
        super("character/survivor.png");
        movementSpeed = 1.0f;
        attackSpeed = 1.0f;
        damage = 1.0f;
        state = Globals.EntityState.STANDING;
        direction = Globals.Direction.N;
        //setPosition(Gdx.graphics.getWidth() / 2 - getWidth() / 2, Gdx.graphics.getHeight() / 2 - getHeight() / 2);
        setPosition(0,0);
        position = new Vector2(getX(), getY());
        directionVector = new Vector2(0,0);
        attackCooldown = 0;
        onCooldown = false;
        hitbox = new Circle(getX()+getWidth()/2, getY()+getHeight()/2, getWidth()/3);
        this.addAction(updateAction());
    }

    protected Action updateAction() {
        return new Action() {
            @Override
            public boolean act(float delta) {
                if(onCooldown) {
                    attackCooldown += delta;
                    if(attackCooldown >= attackSpeed){
                        attackCooldown = delta;
                        onCooldown = false;
                    }
                }
                hitbox.setPosition(position);
                return false;
            }
        };
    }

    protected void move(float dx, float dy) {
        setX(getX() + dx * movementSpeed * 8.0f * Globals.SCALE);
        setY(getY() + dy * movementSpeed * 8.0f * Globals.SCALE);
        position.x = getX()+getWidth()/2;
        position.y = getY()+getHeight()/2;
    }

    protected void rotate(float dx, float dy) {
        directionVector.set(dx, dy);
        sprite.setRotation(directionVector.angle());
    }

    protected Bullet getBullet(float dx, float dy){
        onCooldown = true;
        return new Bullet((getX()+getWidth()/2)+(dx*(getWidth()/2)), (getY()+getHeight()/2)+(dy*(getHeight()/2)), damage, dx, dy);
    }

    protected void upgradeSpeed(){
        movementSpeed += 0.1f;
    }

    protected void upgradeAttackSpeed(){
        if(attackSpeed > 0.1f)
            attackSpeed -= 0.1f;
    }
}
