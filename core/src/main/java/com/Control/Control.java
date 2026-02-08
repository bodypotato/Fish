package com.Control;

import com.Control.Global.AllToolsInit;
import com.Control.Global.BaseTools;
import com.Control.Global.LoadGlobalAsset;
import com.Control.Global.MyAtlas;
import com.Screen.Choose.Choose;
import com.Screen.Start.Start;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;


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
        AllToolsInit.init();
        LoadGlobalAsset.load();
        // 初始化单例
        instance = this;
        //初始化必要的Screen
        BaseTools.getInstance().startScreen = new Start(this);
        BaseTools.getInstance().chooseScreen = new Choose(this);
        goToStartScreen();

        Gdx.app.log("Control", "游戏初始化完成，已进入开始界面");
    }

    public void goToStartScreen(){
        setScreen(BaseTools.getInstance().startScreen);
    }
    public void goToChooseScreen(){
        setScreen(BaseTools.getInstance().chooseScreen);
    }
    public void goToVictoryEndScreen(){

    }
    public void goToDieEndScreen(){

    }
    @Override
    public void render() {
        BaseTools.getInstance().gameTime += Gdx.graphics.getDeltaTime();
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        BaseTools.getInstance().uiViewport.update(width, height,true); // 视口更新会自动更新相机
        super.resize(width, height);
    }

    @Override
    public void dispose() {

    }
}
