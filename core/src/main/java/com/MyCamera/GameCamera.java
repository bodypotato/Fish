package com.MyCamera;

import character.ICharacter;
import com.Control.Global.BaseTool;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameCamera implements ICamera{
    Camera camera;
    float left;
    float right;
    float top;
    float bottom;
    Sprite sprite;
    public GameCamera(float worldWidth, float worldHeight){
        camera = new OrthographicCamera();
        left = 0;
        right = worldWidth;
        top = worldHeight;
        bottom = 0;
    }
    public void correctCamera(ICharacter object){
        sprite = new Sprite(object.getSprite());
        updateCenterCamera(sprite);
        restrictCamera(sprite);
    }
    public void updateCenterCamera(Sprite sprite) {
        float centerX = sprite.getX() + sprite.getWidth() / 2;
        float centerY = sprite.getY() + sprite.getHeight() / 2;
        camera.position.set(centerX,centerY , 0);
        camera.update();
        left = centerX - BaseTool.getInstance().gameViewport.getWorldWidth()/2;
        right = centerX + BaseTool.getInstance().gameViewport.getWorldWidth()/2;
        top = centerY + BaseTool.getInstance().gameViewport.getWorldHeight()/2;
        bottom = centerY - BaseTool.getInstance().gameViewport.getWorldHeight()/2;
    }
    public void restrictCamera(Sprite sprite) {
    }

    public Camera getCamera(){
        return camera;
    }
}
