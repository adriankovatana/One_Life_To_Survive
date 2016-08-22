package edu.uco.kkovatana1.olts;

public class GameSession {
    protected int elapsedTime;
    protected int score;
    protected int shotsFired;
    protected int hits;
    protected int kills;

    public GameSession(){
        elapsedTime = 0;
        score = 0;
        shotsFired = 0;
        hits = 0;
        kills = 0;
    }

    protected void hit(){
        hits++;
        kills++;
        score+=5;
    }

    protected float getAccuracy(){
        return (float)hits/shotsFired;
    }
}
