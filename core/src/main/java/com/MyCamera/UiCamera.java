package com.MyCamera;

import character.ICharacter;
import com.Control.Global.BaseTool;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class UiCamera implements ICamera{
    Camera camera;
    float left;
    float right;
    float top;
    float bottom;
    Sprite sprite;
    public UiCamera(float worldWidth, float worldHeight){
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
        left = centerX - BaseTool.getInstance().uiViewport.getWorldWidth()/2;
        right = centerX + BaseTool.getInstance().uiViewport.getWorldWidth()/2;
        top = centerY + BaseTool.getInstance().uiViewport.getWorldHeight()/2;
        bottom = centerY - BaseTool.getInstance().uiViewport.getWorldHeight()/2;
    }
    public void restrictCamera(Sprite sprite) {
    }
    public Camera getCamera(){
        return camera;
    }
}
