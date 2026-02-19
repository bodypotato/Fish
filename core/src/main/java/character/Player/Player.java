package character.Player;

import character.ICharacter;
import com.Control.Global.BaseTools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player implements ICharacter {
    //初始大小和位置
    private static final float PLAYER_WIDTH = BaseTools.getInstance().gameViewport.getWorldWidth()/5;
    private static final float PLAYER_HEIGHT = BaseTools.getInstance().gameViewport.getWorldHeight()/5;


    //属性
    private Sprite playerSprite;
    private PlayerAnimation playerAnimation;
    private int HP;
    private float weight;
    private float length;
    private float p;//密度
    private float speed;
    private Vector2 position;

    //动画播放
    float aniTime = 0;

    public Player() {
        playerAnimation = new PlayerAnimation();
        playerSprite = new Sprite(playerAnimation.rightAni.getKeyFrame(0));
        initProperty();
        //初始化Sprite属性
        setSpriteSize(PLAYER_WIDTH,PLAYER_HEIGHT);

    }

    @Override
    public void initProperty() {
        HP = 100;
        weight = 1f;
        length = 1f;
        p = weight/length;
        speed = 100f;
        position = new Vector2();
    }

    @Override
    public void setSpriteSize(float width, float height) {
        playerSprite.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
    }

    @Override
    public Sprite getSprite() {
        return playerSprite;
    }

    public void move(float delta){
        aniTime+=delta;
        playerAnimation.dirJudge();
        //移动
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            playerSprite.translateY(speed*delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            playerSprite.translateY(-speed*delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            playerAnimation.aniPlay(playerAnimation.getDir(),playerSprite,aniTime);
            playerSprite.translateX(-speed*delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            playerAnimation.aniPlay(playerAnimation.getDir(),playerSprite,aniTime);
            playerSprite.translateX(speed*delta);
        }


        position.set(playerSprite.getX(), playerSprite.getY());
//        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
//            playerSprite.setAlpha(0.5f);
//        }
    }

    public void draw(Batch batch) {
        playerSprite.draw(batch);
    }
    //getter
    public int getHP() {return HP;}
    public float getWeight() {return weight;}
    public float getLength() {return length;}
    public float getP() {return p;}
    public float getSpeed() {return speed;}
    public Vector2 getPosition() {return position.cpy();}

    //setter
    public void setHP(int hp) {this.HP = hp;}
    public void setWeight(float weight) {this.weight = weight;}
    public void setLength(float length) {this.length = length;}
    public void setSpeed(float speed) {this.speed = speed;}
    public void setPosition(Vector2 position) {
        this.position = position.cpy();
        playerSprite.setCenter(position.x, position.y);
    }

    //
}
