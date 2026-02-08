package com.Screen.Start;

import com.Control.Control;
import com.Control.Global.BaseTools;
import com.Control.Global.AllPath;
import com.Screen.My_GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Start implements Screen , My_GUI {

    private final Control control;
    private Texture background;
    private Sprite begin;
    private Sprite exit;
    private Rectangle beginRect;
    private Rectangle exitRect;
    Vector2 touchPos;
    public Start(Control control) {
        this.control = control;
    }

    @Override
    public void show() {
        loadAssets();
        initUI();
        Gdx.app.log("StartScreen","Init");
    }

    @Override
    public void loadAssets() {
        BaseTools.getInstance().assetManagerLoad(AllPath.START_BACKGROUND, Texture.class);
    }
    @Override
    public void initUI(){
        background = BaseTools.getInstance().assetManagerGet(AllPath.START_BACKGROUND, Texture.class);
        begin = new Sprite(BaseTools.getInstance().assetManagerGet(AllPath.BUTTON_BEGIN, Texture.class));
        exit = new Sprite(BaseTools.getInstance().assetManagerGet(AllPath.BUTTON_EXIT, Texture.class));
        begin.setSize(BaseTools.getInstance().uiViewport.getWorldWidth()/5, BaseTools.getInstance().uiViewport.getWorldHeight()/5);
        exit.setSize(BaseTools.getInstance().uiViewport.getWorldWidth()/5, BaseTools.getInstance().uiViewport.getWorldHeight()/5);
        beginRect = new Rectangle();
        exitRect = new Rectangle();
        touchPos = new Vector2();
    }

    @Override
    public void render(float delta) {
        //随视口变大变小实时更新位置
        begin.setCenter(BaseTools.getInstance().uiViewport.getWorldWidth()/2-begin.getWidth(), BaseTools.getInstance().uiViewport.getWorldHeight()/2);
        exit.setCenter(BaseTools.getInstance().uiViewport.getWorldWidth()/2+begin.getWidth(), BaseTools.getInstance().uiViewport.getWorldHeight()/2);
        input(delta);
        draw();
    }

    @Override
    public void input(float delta){
        beginRect.set(begin.getX(), begin.getY(), begin.getWidth(), begin.getHeight());
        exitRect.set(exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight());
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            BaseTools.getInstance().uiViewport.unproject(touchPos);
            if(beginRect.contains(touchPos)){
                control.goToChooseScreen();
            }
            if(exitRect.contains(touchPos)){
                Gdx.app.exit();
            }
        }

    }

    @Override
    public void draw(){
        ScreenUtils.clear(Color.BLACK);
        BaseTools.getInstance().uiViewport.apply();
        BaseTools.getInstance().batch.setProjectionMatrix(BaseTools.getInstance().uiCamera.getCamera().combined);
        BaseTools.getInstance().batch.begin();
        BaseTools.getInstance().batch.draw(background,0,0, BaseTools.getInstance().uiViewport.getWorldWidth(), BaseTools.getInstance().uiViewport.getWorldHeight());
        begin.draw(BaseTools.getInstance().batch);
        exit.draw(BaseTools.getInstance().batch);
        BaseTools.getInstance().batch.end();
    }

    @Override
    public void resize(int width, int height) {
        BaseTools.getInstance().uiViewport.update(width, height,true);
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
