package com.Control.Global;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LoadGlobalAsset {

    private LoadGlobalAsset(){}
    public static void load(){
        assetManagerLoad();
        atlasGet();
    }
    // ===================== 预加载所有全局通用资源=====================
    private static void assetManagerLoad() {
        BaseTool.getInstance().assetManagerLoad(AtlasPath.BUTTON_BEGIN,Texture.class);
        BaseTool.getInstance().assetManagerLoad(AtlasPath.BUTTON_EXIT,Texture.class);
        BaseTool.getInstance().assetManagerLoad(AtlasPath.PLAYER_ATLAS, TextureAtlas.class);
    }

    private static void atlasGet(){
        MyAtlas.getInstance().playerAtlas = BaseTool.getInstance().assetManagerGet(AtlasPath.PLAYER_ATLAS, TextureAtlas.class);
    }


}
