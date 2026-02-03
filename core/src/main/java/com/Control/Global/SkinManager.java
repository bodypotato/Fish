package com.Control.Global;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

//主要用于UI
public class SkinManager {
    // 1. 静态私有单例实例（全局唯一）
    private static volatile SkinManager instance;
    // 核心：全局唯一的Skin对象
    private Skin skin;

    // 2. 私有构造方法（禁止外部new，保证单例）
    private SkinManager() {}

    // 3. 公共静态方法：获取单例实例（双重校验锁，线程安全，LibGDX主线程运行可省略volatile，加了更严谨）
    public static SkinManager getInstance() {
        if (instance == null) {
            synchronized (SkinManager.class) {
                if (instance == null) {
                    instance = new SkinManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化加载Skin（★★★只调用一次★★★，建议在Game的create()中执行）
     * 支持默认Skin/自定义Skin，按需注释切换
     */
    public void init() {
        if (skin != null) return; // 避免重复初始化
        try {
            // 方式1：加载官方默认Skin（快速测试用）
            skin = new Skin(Gdx.files.internal("uiskin.json"));

            // 方式2：加载自定义Skin（实际项目用，注释掉方式1，打开这个）
//            TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/my-skin.atlas"));
//            skin = new Skin(Gdx.files.internal("ui/my-skin.json"), atlas);

            Gdx.app.log("SkinManager", "Skin初始化成功！");
        } catch (Exception e) {
            Gdx.app.error("SkinManager", "Skin初始化失败！原因：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取全局Skin实例（所有界面都用这个方法获取）
     */
    public Skin getSkin() {
        if (skin == null) {
            Gdx.app.error("SkinManager", "Skin还未初始化！请先调用init()方法");
            throw new NullPointerException("Skin is null, call init() first!");
        }
        return skin;
    }

    // 【可选封装】快速获取常用控件样式，简化代码（不用每次都get(Style.class)）
    public TextButtonStyle getTextButtonStyle() {
        return getSkin().get(TextButtonStyle.class);
    }
    public LabelStyle getLabelStyle() {
        return getSkin().get(LabelStyle.class);
    }
    // 其他样式（ScrollPaneStyle/TextFieldStyle）按需添加...

    /**
     * 切换Skin（比如日间/夜间模式，可选功能）
     * @param atlasPath 新图集路径
     * @param jsonPath 新JSON配置路径
     */
    public void switchSkin(String atlasPath, String jsonPath) {
        // 先释放旧Skin
        dispose();
        // 加载新Skin
        TextureAtlas newAtlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        skin = new Skin(Gdx.files.internal(jsonPath), newAtlas);
        Gdx.app.log("SkinManager", "Skin切换成功：" + jsonPath);
    }

    /**
     * 释放Skin资源（★★★只调用一次★★★，建议在Game的dispose()中执行）
     */
    public void dispose() {
        if (skin != null) {
            skin.dispose();
            skin = null; // 置空，避免重复释放
            Gdx.app.log("SkinManager", "Skin资源释放成功！");
        }
    }
}
