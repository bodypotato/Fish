package character.Player;

import com.Control.Global.BaseTool;
import com.Control.Global.MyAtlas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAnimation {
    private static final float FRAME_DURATION = 0.1f;

    //最后输入位置方向
    private static final int DIR_NONE = 0;  // 无方向
    private static final int DIR_LEFT = 1;  // 左
    private static final int DIR_RIGHT = 2; // 右
    private static final int DIR_UP = 3;    // 上
    private static final int DIR_DOWN = 4;  // 下

    private int faceDir = DIR_NONE;
    public int lastDir = DIR_RIGHT;

    public Animation<TextureRegion> leftAni;
    public Animation<TextureRegion> rightAni;

    //帧动画管理
    private Array<TextureAtlas.AtlasRegion> leftFrames;
    private Array<TextureAtlas.AtlasRegion> rightFrames;

    public PlayerAnimation() {
        init();
    }

    public void init(){
        leftFrames = MyAtlas.getInstance().playerAtlas.findRegions("L");
        rightFrames = MyAtlas.getInstance().playerAtlas.findRegions("R");
        leftAni = new Animation<>(FRAME_DURATION, leftFrames);
        rightAni = new Animation<>(FRAME_DURATION, rightFrames);
        leftAni.setPlayMode(Animation.PlayMode.LOOP);
        rightAni.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void dirJudge(){
        boolean pressLeft = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean pressRight = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean pressUp = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean pressDown = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);

        // 处理左右同时按下的情况（优先级最高，方向置空）
        if (pressLeft && pressRight) {
            lastDir = faceDir;
            faceDir = DIR_NONE;
        }
        // 按左不按右
        else if (pressLeft) {
            faceDir = DIR_LEFT;
            lastDir = faceDir;
        }
        // 按右不按左
        else if (pressRight) {
            faceDir = DIR_RIGHT;
            lastDir = faceDir;
        }
        // 上下方向（可根据需求补充动画逻辑）
        else if (pressUp) {
            faceDir = DIR_UP;
            lastDir = faceDir;
        }
        else if (pressDown) {
            faceDir = DIR_DOWN;
            lastDir = faceDir;
        }

    }



    public int getDir(){return faceDir;}

    public void aniPlay(int dir, Sprite sprite,float aniTime){
        if(dir == DIR_LEFT ){
            sprite.setRegion(leftAni.getKeyFrame(aniTime));
        }
        else if(dir == DIR_RIGHT){
            sprite.setRegion(rightAni.getKeyFrame(aniTime));
        }
        else if(dir == DIR_UP){

        }
        else if(dir == DIR_DOWN){

        }
        else if(dir == DIR_NONE){
            aniStandPlay();
        }
    }
    public void aniStandPlay(){

    }
}
