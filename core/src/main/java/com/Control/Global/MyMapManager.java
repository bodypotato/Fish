package com.Control.Global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;


public class MyMapManager implements Disposable {
    // ======== 仅保留 2 个核心成员变量（无任何多余） ========
    private TiledMap currentLevelMap;                // 当前关卡地图（创建 Renderer 必需）
    public static OrthogonalTiledMapRenderer currentMapRenderer; // 当前地图渲染器（核心功能载体）

    // ======== 单例（和你的 BaseTools 保持一致的线程安全懒汉式） ========
    private static volatile MyMapManager INSTANCE;
    private MyMapManager() {} // 私有构造，禁止外部 new

    public static MyMapManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MyMapManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyMapManager();

                }
            }
        }
        return INSTANCE;
    }

    public void init(){

    }
    // ======== 核心功能 1：加载关卡地图 + 创建 Renderer ========
    /**
     * 加载指定关卡地图，并创建对应的 MapRenderer
     * @param levelName 关卡名（如 "level_01"）
     * @param unitScale 渲染缩放因子（和你的瓦片尺寸匹配）
     */
    public void loadLevelMap(String levelName, float unitScale) {
        // 1. 先释放上一关的地图/渲染器（避免内存泄漏）
        unloadCurrentLevelMap();

        // 2. 拼接地图路径，复用 BaseTools 加载地图
        String mapPath = "levels/" + levelName + "/map.tmx";
        BaseTools.getInstance().assetManagerLoad(mapPath, TiledMap.class);
        this.currentLevelMap = BaseTools.getInstance().assetManagerGet(mapPath, TiledMap.class);

        // 3. 创建 MapRenderer（复用 BaseTools 的全局 Batch，核心功能）
        if (currentLevelMap != null) {
            SpriteBatch globalBatch = (SpriteBatch) BaseTools.getInstance().batch;
            this.currentMapRenderer = new OrthogonalTiledMapRenderer(currentLevelMap, unitScale, globalBatch);
            // 关联你的 GameCamera（渲染视角对齐）
            this.currentMapRenderer.setView(BaseTools.getInstance().gameCamera.getCamera());
            Gdx.app.log("MyMapManager", "关卡 " + levelName + " 地图渲染器创建成功");
        } else {
            Gdx.app.error("MyMapManager", "关卡 " + levelName + " 地图加载失败，无法创建渲染器");
        }
    }

    // ======== 核心功能 2：获取当前地图渲染器（供渲染逻辑调用） ========
    public OrthogonalTiledMapRenderer getCurrentMapRenderer() {
        return currentMapRenderer;
    }

    // ======== 核心功能 3：释放当前关卡地图/渲染器 ========
    public void unloadCurrentLevelMap() {
        // 释放渲染器
        if (currentMapRenderer != null) {
            currentMapRenderer.dispose();
            currentMapRenderer = null;
        }
//        // 释放地图 + 同步卸载 AssetManager 中的资源
//        if (currentLevelMap != null) {
//            currentLevelMap.dispose();
//            String mapPath = "levels/" + BaseTools.getInstance().gameTime + "/map.tmx"; // 简化：实际可传关卡名，此处仅示例
//            BaseTools.getInstance().assetManager.unload(mapPath);
//            currentLevelMap = null;
//            Gdx.app.log("MyMapManager", "当前关卡地图/渲染器已释放");
//        }
    }

    // ======== 游戏退出时释放所有资源 ========
    @Override
    public void dispose() {
        unloadCurrentLevelMap();
        INSTANCE = null; // 清空单例
    }
}
