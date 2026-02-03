package com.Control.Global;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

// 懒汉式单例（图集管理）
public class MyAtlas {
    // 私有静态实例
    private static volatile MyAtlas INSTANCE;

    // 实例成员（非static）
    public TextureAtlas playerAtlas;

    // 私有构造器
    private MyAtlas() {}

    // 公共getInstance
    public static MyAtlas getInstance() {
        if (INSTANCE == null) {
            synchronized (MyAtlas.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyAtlas();
                    INSTANCE.init();
                }
            }
        }
        return INSTANCE;
    }

    // 私有初始化
    private void init() {
        playerAtlas = new TextureAtlas();
    }

}
