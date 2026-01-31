package character.Player;

import com.Control.Control;
import com.Control.Global;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PlayerAnimation {
    Texture texture;

    public PlayerAnimation() {

        loadAssets();
        initTexture();
    }

    public void loadAssets(){
        try{
            Global.assetManager.load("Character/Player/player.png", Texture.class);

            Global.assetManager.finishLoading();
        }catch(Exception e){
            Gdx.app.log("PlayerAnimation","error loading assets");
            Gdx.app.exit();
        }
    }

    public void initTexture(){
        texture = Global.assetManager.get("Character/Player/player.png", Texture.class);
    }
}
