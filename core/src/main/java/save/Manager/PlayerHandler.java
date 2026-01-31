package save.Manager;

import character.Player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import save.Data.PlayerData;

public class PlayerHandler {
    private static final Json JSON = new Json();

    private PlayerHandler() {}
    public static PlayerData loadPlayer(Player player) {
        PlayerData loadData;
        try {
            // 1. 用SaveManager读取本地JSON文件为字符串（路径用PlayerSaveData的常量）
            String jsonStr = SaveLoadManager.read(PlayerData.PATH);

            // 2. 容错：文件不存在/读取为空（首次游戏，无存档）→ 使用默认存档数据
            if (jsonStr == null || jsonStr.isEmpty()) {
                Gdx.app.log("PlayerSaveHandler", "无存档文件，使用默认玩家数据");
                loadData = syncGetPlayerData(player);
            } else {
                // 3. 反序列化：JSON字符串 → PlayerSaveData对象
                loadData = JSON.fromJson(PlayerData.class, jsonStr);
                Gdx.app.log("PlayerSaveHandler", "存档文件读取成功，开始恢复玩家状态");
            }

            // 4. 核心：把存档数据同步到游戏内的Player对象（处理私有属性+Vector2.cpy()）
            syncSetPlayerData(loadData, player);
            return loadData;

        } catch (Exception e) {
            // 容错：JSON解析失败/文件损坏 → 用默认数据，避免游戏崩溃
            Gdx.app.error("PlayerSaveHandler", "读档失败，原因：" + e.getMessage(), e);
            loadData = syncGetPlayerData(player);
            syncSetPlayerData(loadData, player);
            return loadData;
        }
    }

    public static boolean savePlayer(Player player) {
        if (player == null) {
            Gdx.app.error("PlayerHandler", "存档失败：Player对象为空");
            return false;
        }
        try {
            // 1. 把Player状态同步到PlayerData（处理Vector2.cpy()）
            PlayerData saveData = syncGetPlayerData(player);
            // 2. 序列化：PlayerData → JSON字符串
            String jsonStr = JSON.prettyPrint(saveData);
            // 3. 用SaveLoadManager写入本地文件
            boolean isSuccess = SaveLoadManager.write(PlayerData.PATH, jsonStr);
            if (isSuccess) {
                Gdx.app.log("PlayerHandler", "存档成功：" + PlayerData.PATH);
            } else {
                Gdx.app.error("PlayerHandler", "存档失败：SaveLoadManager写入文件失败");
            }
            return isSuccess;
        } catch (Exception e) {
            Gdx.app.error("PlayerHandler", "存档失败，原因：" + e.getMessage(), e);
            return false;
        }
    }
    private static void syncSetPlayerData(PlayerData saveData, Player player) {
        // 按你的Player存档属性逐一同步，示例为大鱼吃小鱼核心属性，你按实际修改
        player.setHP(saveData.HP);
        player.setWeight(saveData.weight);
        player.setLength(saveData.length);
        player.setSpeed(saveData.speed);
        // 核心细节：Vector2位置必须cpy()，解绑存档数据和游戏实时数据
        player.setPosition(saveData.position.cpy()); // Player的setPosition方法接收Vector2
    }
    private static PlayerData syncGetPlayerData(Player player) {
        PlayerData saveData = new PlayerData();
        // 按你的Player属性逐一读取，用get方法
        saveData.HP = player.getHP();
        saveData.weight = player.getWeight();
        saveData.length = player.getLength();
        saveData.speed = player.getSpeed();
        // 位置cpy()，保存快照
        saveData.position = player.getPosition().cpy();

        return saveData;
    }
    //清除不影响正在进行的游戏
    public static boolean clearPlayerSave() {
        return SaveLoadManager.delete(PlayerData.PATH);
    }

    /**
     * 清空玩家存档+立即重置玩家状态
     * @param player 游戏内的Player实例（清空后立即恢复为原生初始值）
     * @return 是否删除成功
     */
    public static boolean clearPlayerSave(Player player) {
        if (player == null) {
            Gdx.app.error("PlayerHandler", "清空存档失败：Player对象为空");
            return false;
        }
        // 第一步：删除本地存档文件
        boolean isDeleted = SaveLoadManager.delete(PlayerData.PATH);
        // 第二步：立即重置玩家为原生初始值（核心：重新new Player覆盖，复用原生初始值）
        resetPlayerToDefault(player);
        Gdx.app.log("PlayerHandler", "玩家状态已重置为原生初始值");
        return isDeleted;
    }

    // ========== 私有辅助：重置玩家为原生初始值 ==========
    private static void resetPlayerToDefault(Player player) {
        // 新建一个临时Player，获取原生初始值
        Player tempDefaultPlayer = new Player();
        // 用现有同步方法，把初始值同步到游戏内的真实Player
        PlayerData defaultData = syncGetPlayerData(tempDefaultPlayer);
        syncSetPlayerData(defaultData, player);
    }
}
