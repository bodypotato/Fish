package com.Screen.MainGame.Prologue_Level;

import character.Player.Player;
import com.Control.Control;
import com.Control.Global.AllPath;
import com.Control.Global.BaseTools;
import com.Control.Global.MyMapManager;
import com.Screen.My_GUI;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import save.Manager.PlayerHandler;


public class FishFarm implements Screen, My_GUI {
    final Control control;
    TiledMap map;
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
        BaseTools.getInstance().assetManagerLoad(AllPath.FISH_FARM_Background, TiledMap.class);
        map = BaseTools.getInstance().assetManagerGet(AllPath.FISH_FARM_Background, TiledMap.class);
        MyMapManager.currentMapRenderer = new OrthogonalTiledMapRenderer(map,1f,BaseTools.getInstance().batch);
    }

    @Override
    public void initUI() {

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
        BaseTools.getInstance().gameCamera.correctCamera(player);//镜头跟随人物
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
        MyMapManager.currentMapRenderer.setView(BaseTools.getInstance().gameCamera.getCamera());
        MyMapManager.currentMapRenderer.render();
        BaseTools.getInstance().gameViewport.apply();
        BaseTools.getInstance().batch.setProjectionMatrix(BaseTools.getInstance().gameCamera.getCamera().combined);
        BaseTools.getInstance().batch.begin();

        player.draw(BaseTools.getInstance().batch);
        BaseTools.getInstance().batch.end();
    }

    @Override
    public void resize(int width, int height) {
        BaseTools.getInstance().gameViewport.update(width, height);
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
