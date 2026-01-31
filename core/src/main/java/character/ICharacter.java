package character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface ICharacter {
    void initProperty();
    void initSize(float width, float height);
    void initPosition(float x, float y);
    void setSpriteSize(float width, float height);
    void setSpritePosition(float x, float y);
    Sprite getSprite();
    void draw(Batch batch);
}
