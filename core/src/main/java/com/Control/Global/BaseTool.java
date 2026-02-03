package com.Control.Global;

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
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// 严格懒汉式单例（线程安全，适配游戏多线程场景）
public class BaseTool {
    //  私有静态实例（唯一实例）
    private static volatile BaseTool INSTANCE;

    //  私有构造器（禁止外部new）
    private BaseTool() {}

    // 实例成员
    //--全局Screen
    public Start startScreen;
    public Choose chooseScreen;

    //全局工具
    private AssetManager assetManager;
    public Batch batch;
    public BitmapFont font;
    public Viewport uiViewport;
    public UiCamera uiCamera;
    public Viewport gameViewport;
    public GameCamera gameCamera;
    public float gameTime;

    // 游戏常量（仍为static final，不可修改，无封装风险）
    public static final int LOGICAL_WIDTH = 1600;
    public static final int LOGICAL_HEIGHT = 900;
    public static final float FONT_SCALE = 2.0f;

    // 4. 公共静态getInstance（双重校验锁，线程安全+懒加载）
    public static BaseTool getInstance() {
        if (INSTANCE == null) {
            synchronized (BaseTool.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BaseTool();
                    INSTANCE.init(); // 实例创建时自动初始化，无需手动调用init()
                }
            }
        }
        return INSTANCE;
    }

    // 5. 私有初始化方法（仅实例创建时执行一次）
    private void init() {
        uiViewport = new ExtendViewport(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        uiCamera = new UiCamera(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        uiViewport.setCamera(uiCamera.getCamera());
        uiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        gameViewport = new ExtendViewport(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        gameCamera = new GameCamera(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        gameViewport.setCamera(gameCamera.getCamera());
        gameViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        assetManager = new AssetManager(new InternalFileHandleResolver());
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(FONT_SCALE);
    }

    //==================getter=========================

    public <T> T assetManagerGet(String path, Class<T> type) {
        if (path == null || type == null || !assetManager.isLoaded(path, type)) {
            Gdx.app.error(type == null ? "BaseTool" : type.getSimpleName(), "资源未加载：" + path);
            return null;
        }
        return assetManager.get(path, type);
    }


    // ===============setter========================
    public <T> void assetManagerLoad(String path, Class<T> type) {
        // 1. 前置参数校验：提前拦截非法值，开发阶段就报错
        if (path == null || path.trim().isEmpty()) {
            Gdx.app.error("BaseTool", "资源加载失败：路径不能为空！");
            return;
        }
        if (type == null) {
            Gdx.app.error("BaseTool", "资源加载失败：类型令牌不能为空！");
            return;
        }
        // 2. 避免重复加载：提升性能，防止资源冗余
        if (assetManager.isLoaded(path, type)) {
            Gdx.app.log(type.getSimpleName(), "资源已加载，跳过：" + path);
            return;
        }
        // 3. 同步加载资源，捕获所有异常
        try {
            assetManager.load(path, type);
            // 精准加载当前资源，而非加载所有待加载资源（原finishLoading()的优化）
            assetManager.finishLoadingAsset(path);
            Gdx.app.log(type.getSimpleName(), "资源加载成功：" + path);
        } catch (Exception e) {
            // 日志打印：类名 + 路径 + 完整异常栈，方便排查问题
            Gdx.app.error(type.getSimpleName(), "资源加载失败：" + path, e);
            Gdx.app.exit(); // 核心资源加载失败，退出应用
        }
    }
}
