package com.MyCamera;

import character.ICharacter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICamera {
    public void correctCamera(ICharacter object);
    public void updateCenterCamera(Sprite sprite);
    public void restrictCamera(Sprite sprite);
    public Camera getCamera();
}
