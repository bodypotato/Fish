package com.Screen.Choose;

import com.Control.Control;
import com.Control.Global;
import com.Screen.MainGame.Prologue_Level.FishFarm;
import com.Screen.My_GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class Choose implements Screen, My_GUI {
    private final Control control;
    private Texture background;
    public Choose(Control control) {
        this.control = control;
    }
    @Override
    public void show() {
        loadAssets();
        initUI();
        Gdx.app.log("Choose","Init");
    }

    @Override
    public void loadAssets() {
        try {
            Global.assetManager.load("Screen/Choose/background.png", Texture.class);
            Global.assetManager.finishLoading();
        } catch (Exception e) {
            Gdx.app.error("Choose", "资源加载失败：" + e.getMessage(), e);
            Gdx.app.exit();
        }
    }

    @Override
    public void initUI() {
        background = Global.assetManager.get("Screen/Choose/background.png", Texture.class);

    }


    @Override
    public void render(float delta) {

        input(delta);
        draw();
    }

    @Override
    public void input(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            goToFishFarm();
        }
    }

    @Override
    public void draw(){
        ScreenUtils.clear(Color.BLACK);
        Global.uiViewport.apply();
        Global.batch.setProjectionMatrix(Global.uiCamera.getCamera().combined);
        Global.batch.begin();
        Global.batch.draw(background,0,0,Global.uiViewport.getWorldWidth(),Global.uiViewport.getWorldHeight());
        Global.batch.end();
    }

    public void goToFishFarm(){
        control.setScreen(new FishFarm(control));
    }

    public void goToGame2(){

    }

    public void goToGame3(){

    }

    @Override
    public void resize(int width, int height) {

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
