package com.Control;

import com.MyCamera.UiCamera;
import com.Screen.Choose.Choose;
import com.Screen.Start.Start;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Control extends Game {
    private static Control instance;

    //Other package get Instance
    public static Control getInstance() {
        if (instance == null) {
            throw new GdxRuntimeException("Control尚未初始化！请先调用create()方法");
        }
        return instance;
    }


    @Override
    public void create() {
        // 初始化单例
        instance = this;
        //初始化必要的Screen
        Global.startScreen = new Start(this);
        Global.chooseScreen = new Choose(this);
        goToStartScreen();

        Gdx.app.log("Control", "游戏初始化完成，已进入开始界面");
    }

    public void goToStartScreen(){
        setScreen(Global.startScreen);
    }
    public void goToChooseScreen(){
        setScreen(Global.chooseScreen);
    }
    public void goToVictoryEndScreen(){

    }
    public void goToDieEndScreen(){

    }
    @Override
    public void render() {
        Global.gameTime += Gdx.graphics.getDeltaTime();
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        Global.uiViewport.update(width, height,true); // 视口更新会自动更新相机
        super.resize(width, height);
    }

    @Override
    public void dispose() {

    }
}
