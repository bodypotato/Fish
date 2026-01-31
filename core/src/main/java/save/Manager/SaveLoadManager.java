package save.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * 通用文件IO工具类：彻底解耦具体业务，只做字符串的硬盘写入/读取
 * 任何数据要存硬盘，只需外部传「文件路径」+「待写入字符串」，全程复用
 */
public class SaveLoadManager {
    // 无任何硬编码路径，无Json实例（序列化交给外部）
    private SaveLoadManager() {} // 私有构造，禁止创建实例（纯静态工具类）


    public static boolean write(String filePath, String content) {
        // 判空：路径和内容都不能为空
        if (filePath == null || filePath.isEmpty() || content == null) {
            Gdx.app.log("SaveManager", "❌ 写入失败：路径或内容为空");
            return false;
        }

        try {
            FileHandle file = Gdx.files.local(filePath);
            // 自动创建【文件的父文件夹】（适配任意路径，比如save/player、save/item都能自动建save）
            if (!file.parent().exists()) {
                file.parent().mkdirs();
                Gdx.app.log("SaveManager", "✅ 自动创建父文件夹：" + file.parent().path());
            }
            // 核心写入：把传入的字符串写入硬盘，false覆盖旧文件
            file.writeString(content, false);
            Gdx.app.log("SaveManager", "✅ 字符串成功写入：" + file.path());
            return true;
        } catch (Exception e) {
            Gdx.app.log("SaveManager", "❌ 写入失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 通用读取方法：从指定硬盘路径读取字符串
     * @param filePath 要读取的文件完整路径
     * @return 读取到的字符串（null=文件不存在/读取失败）
     */
    public static String read(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            Gdx.app.log("SaveManager", "❌ 读取失败：路径为空");
            return null;
        }

        try {
            FileHandle file = Gdx.files.local(filePath);
            if (!file.exists() || file.length() == 0) {
                Gdx.app.log("SaveManager", "❌ 读取失败：文件不存在或为空");
                return null;
            }
            // 核心读取：从硬盘读取文件内容为字符串
            return file.readString();
        } catch (Exception e) {
            Gdx.app.log("SaveManager", "❌ 读取失败：" + e.getMessage());
            return null;
        }
    }

    public static boolean delete(String path) {
        try {
            FileHandle file = Gdx.files.local(path);
            if (!file.exists()) {
                Gdx.app.log("SaveLoadManager", "删除失败：文件不存在 " + path);
                return true; // 文件本就不存在，视为删除成功，避免业务报错
            }
            boolean isDeleted = file.delete();
            if (isDeleted) {
                Gdx.app.log("SaveLoadManager", "文件删除成功 " + path);
            } else {
                Gdx.app.error("SaveLoadManager", "文件删除失败 " + path);
            }
            return isDeleted;
        } catch (Exception e) {
            Gdx.app.error("SaveLoadManager", "删除文件异常：" + path, e);
            return false;
        }
    }

}
