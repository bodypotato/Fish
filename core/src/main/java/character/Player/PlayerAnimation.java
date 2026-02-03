package character.Player;

import com.Control.Global.BaseTool;
import com.Control.Global.MyAtlas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAnimation {
    private static final float FRAME_DURATION = 0.1f;

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
}
