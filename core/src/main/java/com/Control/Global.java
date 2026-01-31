package com.Control;

import com.MyCamera.GameCamera;
import com.MyCamera.UiCamera;
import com.Screen.Choose.Choose;
import com.Screen.Start.Start;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Global {
    // =====================全局不释放的Screen=========================
    public static Start startScreen;
    public static Choose chooseScreen;

    // ===================== Main Asset=====================
    public static AssetManager assetManager;
    public static Batch batch;
    public static BitmapFont font;
    public static Viewport uiViewport;
    public static UiCamera uiCamera;
    public static Viewport gameViewport;
    public static GameCamera gameCamera;
    public static float gameTime;//游戏总时长

    // ===================== 游戏常量（屏幕适配）=====================
    public static final int LOGICAL_WIDTH = 1600;   // 逻辑宽度
    public static final int LOGICAL_HEIGHT = 900;  // 逻辑高度
    public static final float FONT_SCALE = 2.0f;   // 默认字体缩放系数（解决文字过小问题）

    static {
        initGlobalAssets();
        loadCommonAssets();
    }
    // ===================== 封装：初始化全局资源（优化：增加字体缩放）=====================
    private static void initGlobalAssets() {
        TextureAtlas textureAtlas = new TextureAtlas();
        uiViewport = new ExtendViewport(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        uiCamera = new UiCamera(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        uiViewport.setCamera(uiCamera.getCamera());
        uiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
        gameViewport = new ExtendViewport(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        gameCamera = new GameCamera(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        gameViewport.setCamera(gameCamera.getCamera());
        gameViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
        assetManager = new AssetManager(new InternalFileHandleResolver()); // 优化：指定文件解析器（兼容不同平台）
        batch = new SpriteBatch();
        // 默认字体缩放，解决文字显示过小问题
        font = new BitmapFont();
        font.getData().setScale(FONT_SCALE); // 字体放大2倍

    }

    // ===================== 封装：预加载通用资源（优化：异常处理+简化类型）=====================
    private static void loadCommonAssets() {
        try {
            // 简化Texture/Sound类型（已导入，无需写全类名）
            assetManager.load("Button/begin.png", Texture.class);
            assetManager.load("Button/exit.png", Texture.class);
            // 阻塞加载（确保资源加载完成）
            assetManager.finishLoading();
            Gdx.app.log("Control", "通用资源加载完成，共加载：" + assetManager.getAssetNames().size + "个资源");
        } catch (GdxRuntimeException e) {
            // 资源加载异常处理（避免文件缺失导致崩溃）
            Gdx.app.error("Control", "资源加载失败：" + e.getMessage(), e);
            // 退出游戏（可选：也可跳转到错误界面）
            Gdx.app.exit();
        }
    }

}
