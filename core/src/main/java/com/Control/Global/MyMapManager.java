package com.Control.Global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;



/**
 * 地图管理器（单例）
 * 职责：
 *   1. 加载 / 卸载关卡地图，创建 MapRenderer
 *   2. 提供对象层（Object Layer）的统一访问接口
 *      - 按层名/对象名/自定义属性查询各类型对象
 *      - 提取出生点、碰撞区域、触发器等常用数据
 */
public class MyMapManager implements Disposable {

    // ───────────────────────── 成员变量 ─────────────────────────

    private TiledMap currentLevelMap;
    public static OrthogonalTiledMapRenderer currentMapRenderer;

    /**
     * 缓存：层名 → 该层所有对象（避免每帧重复查找）
     * 在 loadLevelMap / loadDirectMap 成功后由 cacheObjectLayers() 填充
     */
    private final ObjectMap<String, MapObjects> objectLayerCache = new ObjectMap<>();

    // ───────────────────────── 单例 ─────────────────────────────
    private static volatile MyMapManager INSTANCE;
    private MyMapManager() {}

    public static MyMapManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MyMapManager.class) {
                if (INSTANCE == null){
                    INSTANCE = new MyMapManager();
                }
            }
        }
        return INSTANCE;
    }

    // ───────────────────────── 加载地图 ─────────────────────────

    public void loadLevelMap(String mapPath, float unitScale) {
        unloadCurrentLevelMap();
        BaseTools.getInstance().assetManagerLoad(mapPath, TiledMap.class);
        this.currentLevelMap = BaseTools.getInstance().assetManagerGet(mapPath, TiledMap.class);
        initRenderer(unitScale, "关卡 " + mapPath);
    }


    /** 创建 Renderer 并缓存所有对象层（内部公共逻辑） */
    private void initRenderer(float unitScale, String logTag) {
        if (currentLevelMap == null) {
            Gdx.app.error("MyMapManager", logTag + " 地图为 null，无法创建渲染器");
            return;
        }
        SpriteBatch globalBatch = (SpriteBatch) BaseTools.getInstance().batch;
        currentMapRenderer = new OrthogonalTiledMapRenderer(currentLevelMap, unitScale, globalBatch);
        currentMapRenderer.setView(BaseTools.getInstance().gameCamera.getCamera());
        cacheObjectLayers();
        Gdx.app.log("MyMapManager", logTag + " 渲染器创建成功，对象层数量：" + objectLayerCache.size);
    }

    // ───────────────────────── 对象层缓存 ────────────────────────

    /** 遍历地图所有层，将对象层写入缓存 */
    private void cacheObjectLayers() {
        objectLayerCache.clear();
        if (currentLevelMap == null) return;
        for (MapLayer layer : currentLevelMap.getLayers()) {
            // 只缓存含有 MapObjects 的层（即对象层）
            MapObjects objects = layer.getObjects();
            if (objects != null && objects.getCount() > 0) {
                objectLayerCache.put(layer.getName(), objects);
                Gdx.app.log("MyMapManager", "缓存对象层：" + layer.getName()
                    + "（" + objects.getCount() + " 个对象）");
            }
        }
    }

    // -------------------------Render----------------------------
    public void MapRender(){
        currentMapRenderer.setView(BaseTools.getInstance().gameCamera.getCamera());
        currentMapRenderer.render();
    }
    // ───────────────────────── 对象层查询 API ────────────────────

    /**
     * 获取指定层的全部对象
     * @param layerName Tiled 里对象层的名称
     * @return MapObjects，若层不存在返回 null
     */
    public MapObjects getObjects(String layerName) {
        return objectLayerCache.get(layerName, null);
    }

    /**
     * 按对象名称查找单个对象（在指定层中）
     * @param layerName 层名
     * @param objectName 对象名
     * @return 找到的第一个同名 MapObject，不存在返回 null
     */
    public MapObject getObjectByName(String layerName, String objectName) {
        MapObjects objects = getObjects(layerName);
        if (objects == null) return null;
        return objects.get(objectName);
    }

    /**
     * 按自定义属性键值对查找对象列表（在指定层中）
     * 例如：查找所有 type="enemy" 的对象
     * @param layerName  层名
     * @param propKey    属性键
     * @param propValue  期望的属性值（String 比较）
     */
    public Array<MapObject> getObjectsByProperty(String layerName, String propKey, String propValue) {
        Array<MapObject> result = new Array<>();
        MapObjects objects = getObjects(layerName);
        if (objects == null) return result;
        for (MapObject obj : objects) {
            MapProperties props = obj.getProperties();
            if (props.containsKey(propKey)) {
                Object val = props.get(propKey);
                if (propValue.equals(String.valueOf(val))) {
                    result.add(obj);
                }
            }
        }
        return result;
    }

    // ───────────────────────── 常用形状提取 ──────────────────────

    /**
     * 提取指定层所有矩形对象
     */
    public Array<Rectangle> getRectangles(String layerName) {
        Array<Rectangle> result = new Array<>();
        MapObjects objects = getObjects(layerName);
        if (objects == null) return result;
        for (MapObject obj : objects) {
            if (obj instanceof RectangleMapObject) {
                result.add(((RectangleMapObject) obj).getRectangle());
            }
        }
        return result;
    }

    /**
     * 提取指定层所有多边形对象
     */
    public Array<Polygon> getPolygons(String layerName) {
        Array<Polygon> result = new Array<>();
        MapObjects objects = getObjects(layerName);
        if (objects == null) return result;
        for (MapObject obj : objects) {
            if (obj instanceof PolygonMapObject) {
                result.add(((PolygonMapObject) obj).getPolygon());
            }
        }
        return result;
    }

    /**
     * 提取指定层所有折线对象
     */
    public Array<Polyline> getPolylines(String layerName) {
        Array<Polyline> result = new Array<>();
        MapObjects objects = getObjects(layerName);
        if (objects == null) return result;
        for (MapObject obj : objects) {
            if (obj instanceof PolylineMapObject) {
                result.add(((PolylineMapObject) obj).getPolyline());
            }
        }
        return result;
    }

    /**
     * 提取指定层所有圆形对象
     */
    public Array<Circle> getCircles(String layerName) {
        Array<Circle> result = new Array<>();
        MapObjects objects = getObjects(layerName);
        if (objects == null) return result;
        for (MapObject obj : objects) {
            if (obj instanceof CircleMapObject) {
                result.add(((CircleMapObject) obj).getCircle());
            }
        }
        return result;
    }

    // ───────────────────────── 常用场景 API ──────────────────────

    /**
     * 获取出生点坐标（Point 对象）
     * 约定：Tiled 里用一个"点"对象，层名随意，对象名为 spawnPointName
     *
     * 使用示例：
     *   Vector2 spawn = MyMapManager.getInstance().getSpawnPoint("Objects", "PlayerSpawn");
     *   if (spawn != null) player.setPosition(spawn.x, spawn.y);
     */
    public Vector2 getSpawnPoint(String layerName, String spawnPointName) {
        MapObject obj = getObjectByName(layerName, spawnPointName);
        if (obj == null) {
            Gdx.app.log("MyMapManager", "未找到出生点：" + spawnPointName);
            return null;
        }

        MapProperties props = obj.getProperties();
        // Tiled 点对象的坐标存储的所有属性
        float x = props.get("x", 0f, Float.class);
        float y = props.get("y", 0f, Float.class);
        return new Vector2(x, y);
    }

    /**
     * 批量获取某个属性标记为触发器的矩形区域
     * 约定：在 Tiled 对象层中给矩形加自定义属性 trigger=true
     *
     * 使用示例：
     *   Array<Rectangle> triggers = MyMapManager.getInstance().getTriggerRects("Triggers");
     */
    public Array<Rectangle> getTriggerRects(String layerName) {
        Array<Rectangle> result = new Array<>();
        MapObjects objects = getObjects(layerName);
        if (objects == null) return result;
        for (MapObject obj : objects) {
            if (obj instanceof RectangleMapObject) {
                MapProperties props = obj.getProperties();
                Boolean isTrigger = props.get("trigger", false, Boolean.class);
                if (Boolean.TRUE.equals(isTrigger)) {
                    result.add(((RectangleMapObject) obj).getRectangle());
                }
            }
        }
        return result;
    }

    /**
     * 判断某个点是否落在指定层的任意矩形对象内（常用于触发区域检测）
     *
     * 使用示例：
     *   if (MyMapManager.getInstance().isPointInAnyRect("Triggers", player.x, player.y)) { ... }
     */
    public boolean isPointInAnyRect(String layerName, float worldX, float worldY) {
        for (Rectangle rect : getRectangles(layerName)) {
            if (rect.contains(worldX, worldY)) return true;
        }
        return false;
    }

    /**
     * 获取点所在的矩形对象（返回第一个命中的，可用于触发器区分）
     */
    public MapObject getObjectAtPoint(String layerName, float worldX, float worldY) {
        MapObjects objects = getObjects(layerName);
        if (objects == null) return null;
        for (MapObject obj : objects) {
            if (obj instanceof RectangleMapObject) {
                if (((RectangleMapObject) obj).getRectangle().contains(worldX, worldY)) return obj;
            } else if (obj instanceof CircleMapObject) {
                if (((CircleMapObject) obj).getCircle().contains(worldX, worldY)) return obj;
            }
        }
        return null;
    }

    // ───────────────────────── Renderer 相关 ─────────────────────

    public OrthogonalTiledMapRenderer getCurrentMapRenderer() {
        return currentMapRenderer;
    }

    public TiledMap getCurrentLevelMap() {
        return currentLevelMap;
    }

    // ───────────────────────── 释放资源 ─────────────────────────

    public void unloadCurrentLevelMap() {
        if (currentMapRenderer != null) {
            currentMapRenderer.dispose();
            currentMapRenderer = null;
        }
        objectLayerCache.clear();
        currentLevelMap = null;
    }

    @Override
    public void dispose() {
        unloadCurrentLevelMap();
        INSTANCE = null;
    }
}
