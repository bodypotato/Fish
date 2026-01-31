package com.Screen.Start;

import com.Control.Control;
import com.Control.Global;
import com.Screen.My_GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;

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
        try{
            Global.assetManager.load("Screen/Start/background.png", Texture.class);

            Global.assetManager.finishLoading();
        }catch(Exception e){
            Gdx.app.error("Start", "资源加载失败：" + e.getMessage(), e);
            Gdx.app.exit();
        }
    }
    @Override
    public void initUI(){
        background = Global.assetManager.get("Screen/Start/background.png", Texture.class);
        begin = new Sprite(Global.assetManager.get("Button/begin.png", Texture.class));
        exit = new Sprite(Global.assetManager.get("Button/exit.png", Texture.class));
        begin.setSize(Global.uiViewport.getWorldWidth()/5, Global.uiViewport.getWorldHeight()/5);
        exit.setSize(Global.uiViewport.getWorldWidth()/5, Global.uiViewport.getWorldHeight()/5);
        beginRect = new Rectangle();
        exitRect = new Rectangle();
        touchPos = new Vector2();
    }

    @Override
    public void render(float delta) {
        //随视口变大变小实时更新位置
        begin.setCenter(Global.uiViewport.getWorldWidth()/2-begin.getWidth(), Global.uiViewport.getWorldHeight()/2);
        exit.setCenter(Global.uiViewport.getWorldWidth()/2+begin.getWidth(), Global.uiViewport.getWorldHeight()/2);
        input(delta);
        draw();
    }

    @Override
    public void input(float delta){
        beginRect.set(begin.getX(), begin.getY(), begin.getWidth(), begin.getHeight());
        exitRect.set(exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight());
        if(Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            Global.uiViewport.unproject(touchPos);
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
        Global.uiViewport.apply();
        Global.batch.setProjectionMatrix(Global.uiCamera.getCamera().combined);
        Global.batch.begin();
        Global.batch.draw(background,0,0,Global.uiViewport.getWorldWidth(),Global.uiViewport.getWorldHeight());
        begin.draw(Global.batch);
        exit.draw(Global.batch);
        Global.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        Global.uiViewport.update(width, height,true);
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
