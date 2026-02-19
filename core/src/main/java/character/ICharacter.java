package character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICharacter {
    void initProperty();
    void setSpriteSize(float width, float height);
    void setPosition(float x, float y);
    Sprite getSprite();
    void draw(Batch batch);
}
