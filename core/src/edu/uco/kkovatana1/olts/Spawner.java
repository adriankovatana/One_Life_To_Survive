package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Spawner {
    protected final int SOUTHSPAWN = 12;
    protected final int WESTSPAWN = 24;
    protected final int EASTSPAWN = 30;
    protected final int LIMIT = 36;

    protected Enemy enemy;
    protected int spawnAmount;
    protected int currentSpawnAmount;
    protected boolean cell[];

    public Spawner(){
        enemy = new Enemy(0,0, new Vector2(0,0));
        spawnAmount = 0;
        currentSpawnAmount = 0;
        cell = new boolean[LIMIT];
    }

    public void clearCells(){
        for(int i = 0; i < cell.length; i++)
            cell[i] = false;
        currentSpawnAmount = 0;
    }

    public Enemy spawnEnemy(float cameraX, float cameraY, float cameraW, float cameraH, Vector2 target){
        Enemy temp;
        int random = MathUtils.random(LIMIT-1);
        while (cell[random]){
            random = MathUtils.random(LIMIT-1);
        }
        cell[random] = true;
        currentSpawnAmount++;
        if(random < SOUTHSPAWN/2){
            temp = new Enemy(cameraX+(random*enemy.getWidth()),cameraY+cameraH+enemy.getHeight(), target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else if(random < SOUTHSPAWN){
            temp = new Enemy(cameraX-((SOUTHSPAWN-random)*enemy.getWidth()),cameraY+cameraH+enemy.getHeight(), target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else if(random < WESTSPAWN - SOUTHSPAWN/2){
            temp = new Enemy(cameraX+((random-SOUTHSPAWN)*enemy.getWidth()),cameraY-cameraH-enemy.getHeight()*2, target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else if(random < WESTSPAWN){
            temp = new Enemy(cameraX-((WESTSPAWN-random)*enemy.getWidth()),cameraY-cameraH-enemy.getHeight()*2, target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else if(random < EASTSPAWN - SOUTHSPAWN/4){
            temp = new Enemy(cameraX-cameraW-enemy.getWidth()*2,cameraY+((random-WESTSPAWN)*enemy.getHeight()),target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else if(random < EASTSPAWN){
            temp = new Enemy(cameraX-cameraW-enemy.getWidth()*2,cameraY-((EASTSPAWN-random)*enemy.getHeight()),target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else if(random < LIMIT - SOUTHSPAWN/4){
            temp = new Enemy(cameraX+cameraW+enemy.getWidth()*2,cameraY+((random-EASTSPAWN)*enemy.getHeight()),target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        } else {
            temp = new Enemy(cameraX+cameraW+enemy.getWidth()*2,cameraY-((LIMIT-random)*enemy.getHeight()),target);
            temp.movementSpeed = enemy.movementSpeed;
            return temp;
        }
    }
}
