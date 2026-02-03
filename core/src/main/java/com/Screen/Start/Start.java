package com.Screen.Start;

import com.Control.Control;
import com.Control.Global.BaseTool;
import com.Control.Global.AtlasPath;
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
        BaseTool.getInstance().assetManagerLoad(AtlasPath.START_BACKGROUND, Texture.class);
    }
    @Override
    public void initUI(){
        background = BaseTool.getInstance().assetManagerGet(AtlasPath.START_BACKGROUND, Texture.class);
        begin = new Sprite(BaseTool.getInstance().assetManagerGet(AtlasPath.BUTTON_BEGIN, Texture.class));
        exit = new Sprite(BaseTool.getInstance().assetManagerGet(AtlasPath.BUTTON_EXIT, Texture.class));
        begin.setSize(BaseTool.getInstance().uiViewport.getWorldWidth()/5, BaseTool.getInstance().uiViewport.getWorldHeight()/5);
        exit.setSize(BaseTool.getInstance().uiViewport.getWorldWidth()/5, BaseTool.getInstance().uiViewport.getWorldHeight()/5);
        beginRect = new Rectangle();
        exitRect = new Rectangle();
        touchPos = new Vector2();
    }

    @Override
    public void render(float delta) {
        //随视口变大变小实时更新位置
        begin.setCenter(BaseTool.getInstance().uiViewport.getWorldWidth()/2-begin.getWidth(), BaseTool.getInstance().uiViewport.getWorldHeight()/2);
        exit.setCenter(BaseTool.getInstance().uiViewport.getWorldWidth()/2+begin.getWidth(), BaseTool.getInstance().uiViewport.getWorldHeight()/2);
        input(delta);
        draw();
    }

    @Override
    public void input(float delta){
        beginRect.set(begin.getX(), begin.getY(), begin.getWidth(), begin.getHeight());
        exitRect.set(exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight());
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            BaseTool.getInstance().uiViewport.unproject(touchPos);
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
        BaseTool.getInstance().uiViewport.apply();
        BaseTool.getInstance().batch.setProjectionMatrix(BaseTool.getInstance().uiCamera.getCamera().combined);
        BaseTool.getInstance().batch.begin();
        BaseTool.getInstance().batch.draw(background,0,0, BaseTool.getInstance().uiViewport.getWorldWidth(), BaseTool.getInstance().uiViewport.getWorldHeight());
        begin.draw(BaseTool.getInstance().batch);
        exit.draw(BaseTool.getInstance().batch);
        BaseTool.getInstance().batch.end();
    }

    @Override
    public void resize(int width, int height) {
        BaseTool.getInstance().uiViewport.update(width, height,true);
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
