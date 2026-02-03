package com.Screen.MainGame.Prologue_Level;

import character.Player.Player;
import com.Control.Control;
import com.Control.Global.BaseTool;
import com.Control.Global.AtlasPath;
import com.Screen.My_GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import save.Manager.PlayerHandler;


public class FishFarm implements Screen, My_GUI {
    final Control control;
    Texture background;
    Player player;
    public FishFarm(Control control) {
        this.control = control;
    }

    @Override
    public void show() {
        loadAssets();
        initUI();
        player = new Player();
        loadData();
    }

    @Override
    public void loadAssets() {
        BaseTool.getInstance().assetManagerLoad(AtlasPath.FISH_FARM, Texture.class);
    }

    @Override
    public void initUI() {
        background = BaseTool.getInstance().assetManagerGet(AtlasPath.FISH_FARM, Texture.class);
    }

    public void loadData(){
        PlayerHandler.loadPlayer(player);
    }
    public void saveData(){
        PlayerHandler.savePlayer(player);
    }
    public void rePlayGame(){
        PlayerHandler.clearPlayerSave(player);
    }

    @Override
    public void render(float delta) {
        input(delta);
        BaseTool.getInstance().gameCamera.correctCamera(player);//镜头跟随人物
        draw();
    }

    @Override
    public void input(float delta) {
        //存档
        if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            saveData();
        }
        //删档
        if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
            rePlayGame();
        }
        player.move(delta);
    }

    @Override
    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        BaseTool.getInstance().gameViewport.apply();
        BaseTool.getInstance().batch.setProjectionMatrix(BaseTool.getInstance().gameCamera.getCamera().combined);
        BaseTool.getInstance().batch.begin();
        BaseTool.getInstance().batch.draw(background, 0, 0, BaseTool.getInstance().gameViewport.getWorldWidth(), BaseTool.getInstance().gameViewport.getWorldHeight());
        player.draw(BaseTool.getInstance().batch);
        BaseTool.getInstance().batch.end();
    }

    @Override
    public void resize(int width, int height) {
        BaseTool.getInstance().gameViewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
