package com.Screen.Choose;

import com.Control.Control;
import com.Control.Global.BaseTool;
import com.Control.Global.AtlasPath;
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
        BaseTool.getInstance().assetManagerLoad(AtlasPath.CHOOSE_BACKGROUND, Texture.class);
    }

    @Override
    public void initUI() {
        background = BaseTool.getInstance().assetManagerGet(AtlasPath.CHOOSE_BACKGROUND, Texture.class);
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
        BaseTool.getInstance().uiViewport.apply();
        BaseTool.getInstance().batch.setProjectionMatrix(BaseTool.getInstance().uiCamera.getCamera().combined);
        BaseTool.getInstance().batch.begin();
        BaseTool.getInstance().batch.draw(background,0,0, BaseTool.getInstance().uiViewport.getWorldWidth(), BaseTool.getInstance().uiViewport.getWorldHeight());
        BaseTool.getInstance().batch.end();
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
