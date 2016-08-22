package edu.uco.kkovatana1.olts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    protected GameMain game;
    protected Stage stage;
    protected Stage uistage;
    //    protected OrthographicCamera camera;
    protected Group backgroundGroup;
    protected Group itemGroup;
    protected Player player;
    protected Group enemyGroup;
    protected Group bulletGroup;
    protected GameUI gameUI;
    protected Array<Bullet> bullets;
    protected Array<Enemy> enemies;
    protected Array<Upgrade> upgrades;
    protected Spawner spawner;
    protected Timer gameTimer;
    protected Timer roundTimer;
    protected Timer spawnTimer;
    protected int roundInterval;
    protected Globals.GameState gameState;
    protected GameSession gameSession;

    public GameScreen(GameMain game) {
        this.game = game;
    }

    @Override
    public void show() {
//        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
//
//        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, 10f * aspectRatio, 10f);

//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        FitViewport fv = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
//        stage = new Stage(fv);
        stage = new Stage();
        uistage = new Stage();

        //Background Group
        backgroundGroup = new Group();
        Entity background = new Entity("background/concrete.png");
        background.resize(Globals.SCALE * 10);
        backgroundGroup.addActor(background);
        stage.addActor(backgroundGroup);

        //Item Group
        upgrades = new Array<Upgrade>();
        itemGroup = new Group();
        stage.addActor(itemGroup);

        //Bullet Group
        bullets = new Array<Bullet>();
        bulletGroup = new Group();
        stage.addActor(bulletGroup);

        //Player
        player = new Player();
        player.setPosition(background.getWidth() / 2 - player.getWidth() / 2,
                background.getHeight() / 2 - player.getHeight() / 2);
        player.position.x = player.getX() + player.getWidth()/2;
        player.position.y = player.getY() + player.getHeight()/2;
        stage.addActor(player);

        //Enemy Group
        enemies = new Array<Enemy>();
        enemyGroup = new Group();
        stage.addActor(enemyGroup);

        //UI Group
        gameUI = new GameUI(this);
        uistage.addActor(gameUI);

        spawner = new Spawner();
        roundInterval = 5;
        roundTimer = new Timer();
        spawnTimer = new Timer();
        roundTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                spawner.clearCells();
                if(spawner.spawnAmount == spawner.LIMIT*0.25 || spawner.spawnAmount == spawner.LIMIT*0.5 ||
                        spawner.spawnAmount == spawner.LIMIT*0.75)
                    spawner.enemy.movementSpeed += 0.125;
                if(spawner.spawnAmount >= spawner.LIMIT){
                    spawner.spawnAmount = spawner.LIMIT;
                    spawner.enemy.movementSpeed += 0.125;
                } else {
                    spawner.spawnAmount++;
                }

                Gdx.app.log("Round", "" + spawner.spawnAmount);

                spawnTimer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        if(spawner.currentSpawnAmount >= spawner.spawnAmount)
                            return;
                        addEnemies();
                        Gdx.app.log("Spawner", "spawned enemy " + spawner.currentSpawnAmount);
                    }
                },0,(float)roundInterval/spawner.spawnAmount);
            }
        },0,roundInterval);

        gameState = Globals.GameState.PLAYING;
        gameSession = new GameSession();
        gameTimer = new Timer();
        gameTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                gameSession.elapsedTime++;
            }
        },0,1);

        Gdx.input.setInputProcessor(uistage);
    }

    @Override
    public void render(float delta) {
        if(gameState == Globals.GameState.PLAYING)
            update(delta);
        draw(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
        stage.dispose();
    }

    private void update(float delta) {
        //INPUT
        if(gameUI.movementpad.isTouched())
            player.move(gameUI.movementpad.getKnobPercentX(), gameUI.movementpad.getKnobPercentY());
        if(gameUI.shootingpad.isTouched()) {
            player.rotate(gameUI.shootingpad.getKnobPercentX(), gameUI.shootingpad.getKnobPercentY());
            if(!player.onCooldown){
                addBullet();
            }
        }

        //UPDATE
        //Center Camera
        stage.getCamera().position.x = player.getX() + player.getWidth()/2;
        stage.getCamera().position.y = player.getY() + player.getHeight()/2;

        //Collision
        collisionBulletEnemy();
        collisionPlayerItem();
        if(collisionPlayerEnemy())
            return;

        stage.act();
        uistage.act();
    }

    private void draw(float delta) {
        //RENDER
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        uistage.draw();
    }

    private void addBullet(){
        float angle = new Vector2(gameUI.shootingpad.getKnobPercentX(),
                gameUI.shootingpad.getKnobPercentY()).angle();
        Bullet bullet = player.getBullet(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle));
        bullets.add(bullet);
        bulletGroup.addActor(bullet);
        gameSession.shotsFired++;
    }

    private void addEnemies(){
        Enemy enemy = spawner.spawnEnemy(stage.getCamera().position.x, stage.getCamera().position.y,
                stage.getCamera().viewportWidth / 2, stage.getCamera().viewportHeight / 2, player.position);
        enemies.add(enemy);
        enemyGroup.addActor(enemy);
    }

    private void addItem(float x, float y){
        float dropRate = MathUtils.random(100);
        if(dropRate < 12.5){
            int random = MathUtils.random(100);
            Upgrade upgrade;
            if(random % 5 == 0)
                upgrade = new Upgrade(x,y,"upgrade/speed.png",Globals.UpgradeType.SPEED);
            else
                upgrade = new Upgrade(x,y,"upgrade/firerate.png",Globals.UpgradeType.FIRERATE);
            upgrades.add(upgrade);
            itemGroup.addActor(upgrade);
        }
    }

    private void collisionBulletEnemy(){
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()){
            Bullet b = bulletIterator.next();
            if(offScreen(b.getX(), b.getY())){
                bulletIterator.remove();
                b.remove();
                continue;
            }

            Iterator<Enemy> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()){
                Enemy e = enemyIterator.next();
                if(Intersector.overlaps(b.hitbox, e.hitbox)){
                    addItem(e.getX(),e.getY());
                    enemyIterator.remove();
                    e.remove();
                    bulletIterator.remove();
                    b.remove();
                    gameSession.hit();
                    gameUI.score.text = ""+gameSession.score;
                    break;
                }
            }
        }
    }

    private boolean collisionPlayerEnemy(){
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()){
            Enemy e = enemyIterator.next();
            if(Intersector.overlaps(e.hitbox, player.hitbox)){
                gameOver();
                return true;
            }
        }
        return false;
    }

    private void collisionPlayerItem(){
        Iterator<Upgrade> itemIter = upgrades.iterator();
        while (itemIter.hasNext()){
            Upgrade u = itemIter.next();
            if(Intersector.overlaps(u.hitbox, player.hitbox)){
                if(u.type == Globals.UpgradeType.SPEED) {
                    player.upgradeSpeed();
                    gameUI.speedIcon.upgrade();
                } else {
                    player.upgradeAttackSpeed();
                    gameUI.firerateIcon.upgrade();
                }
                itemIter.remove();
                u.remove();
                gameSession.score++;
                gameUI.score.text = ""+gameSession.score;
            }
        }
    }

    private boolean offScreen(float x, float y){
        float cx = stage.getCamera().position.x;
        float cy = stage.getCamera().position.y;
        float cwidth = stage.getCamera().viewportWidth/2;
        float cheight = stage.getCamera().viewportHeight/2;
        if(x < cx - cwidth || x > cx + cwidth || y < cy - cheight || y > cy + cheight)
            return true;
        return false;
    }

    protected void gameOver(){
        roundTimer.stop();
        spawnTimer.stop();
        gameState = Globals.GameState.PAUSED;
        player.remove();
        gameUI.gameOver();
    }

    protected void pauseSession(boolean b){
        if(b){
            gameState = Globals.GameState.PAUSED;
            gameTimer.stop();
            roundTimer.stop();
            spawnTimer.stop();
        } else {
            gameState = Globals.GameState.PLAYING;
            gameTimer.start();
            roundTimer.start();
            spawnTimer.start();
        }
    }

    protected void quitSession(){
        game.setScreen(game.startScreen);
    }
}