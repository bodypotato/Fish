package com.Screen.Choose;

import com.Control.Control;
import com.Control.Global.BaseTools;
import com.Control.Global.AllPath;
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
        BaseTools.getInstance().assetManagerLoad(AllPath.CHOOSE_BACKGROUND, Texture.class);
    }

    @Override
    public void initUI() {
        background = BaseTools.getInstance().assetManagerGet(AllPath.CHOOSE_BACKGROUND, Texture.class);
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
        BaseTools.getInstance().uiViewport.apply();
        BaseTools.getInstance().batch.setProjectionMatrix(BaseTools.getInstance().uiCamera.getCamera().combined);
        BaseTools.getInstance().batch.begin();
        BaseTools.getInstance().batch.draw(background,0,0, BaseTools.getInstance().uiViewport.getWorldWidth(), BaseTools.getInstance().uiViewport.getWorldHeight());
        BaseTools.getInstance().batch.end();
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
