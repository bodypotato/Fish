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
        BaseTools.getInstance().assetManagerLoad(AllPath.BUTTON_BEGIN,Texture.class);
        BaseTools.getInstance().assetManagerLoad(AllPath.BUTTON_EXIT,Texture.class);
        BaseTools.getInstance().assetManagerLoad(AllPath.PLAYER_ATLAS, TextureAtlas.class);
    }

    private static void atlasGet(){
        MyAtlas.getInstance().playerAtlas = BaseTools.getInstance().assetManagerGet(AllPath.PLAYER_ATLAS, TextureAtlas.class);
    }


}
