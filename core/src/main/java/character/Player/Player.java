package character.Player;

import character.ICharacter;
import com.Control.Control;
import com.Control.Global;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player implements ICharacter {
    private Sprite playerSprite;
    private PlayerAnimation playerAnimation;
    private int HP;
    private float weight;
    private float length;
    private float p;//密度
    private float speed;
    private Vector2 position;
    public Player() {
        playerAnimation = new PlayerAnimation();
        playerSprite = new Sprite(playerAnimation.texture);
        initProperty();
        initSize(Global.gameViewport.getWorldWidth()/5, Global.gameViewport.getWorldHeight()/5);
        initPosition(Global.gameViewport.getWorldWidth()/2, Global.gameViewport.getWorldHeight()/2);
    }

    @Override
    public void initProperty() {
        HP = 100;
        weight = 1f;
        length = 1f;
        p = weight/length;
        speed = 1000f;
        position = new Vector2();
    }

    @Override
    public void initSize(float width, float height) {
        playerSprite.setSize(width, height);
    }

    @Override
    public void initPosition(float x, float y) {
        position.set(x, y);
        playerSprite.setCenter(position.x, position.y);

    }

    @Override
    public void setSpriteSize(float width, float height) {
        playerSprite.setSize(width, height);
    }

    @Override
    public void setSpritePosition(float x, float y) {
        position.set(x, y);
        playerSprite.setPosition(position.x, position.y);
    }

    @Override
    public Sprite getSprite() {
        return playerSprite;
    }

    public void move(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)){
            playerSprite.translateY(speed*delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            playerSprite.translateY(-speed*delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            playerSprite.translateX(-speed*delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            playerSprite.translateX(speed*delta);
        }
        position.set(playerSprite.getX(), playerSprite.getY());
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
