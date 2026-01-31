package com.Screen.MainGame.Prologue_Level;

import character.Player.Player;
import com.Control.Control;
import com.Control.Global;
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
        try {
            Global.assetManager.load("Screen/MainGame/Prologue_Level/fishFarm.png", Texture.class);
            Global.assetManager.finishLoading();
            Gdx.app.log("FishFarm","loadAssets finish");
        } catch (Exception e) {
            Gdx.app.log("FishFarm", "Error loading assets");
            Gdx.app.exit();
        }

    }

    @Override
    public void initUI() {
        background = Global.assetManager.get("Screen/MainGame/Prologue_Level/fishFarm.png", Texture.class);
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
        Global.gameCamera.correctCamera(player);//镜头跟随人物
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
        Global.gameViewport.apply();
        Global.batch.setProjectionMatrix(Global.gameCamera.getCamera().combined);
        Global.batch.begin();
        Global.batch.draw(background, 0, 0, Global.gameViewport.getWorldWidth(), Global.gameViewport.getWorldHeight());
        player.draw(Global.batch);
        Global.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Global.gameViewport.update(width, height);
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
