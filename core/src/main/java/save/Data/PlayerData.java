package save.Data;

import character.Player.Player;
import com.badlogic.gdx.math.Vector2;

public class PlayerData {
    public transient static final String PATH = "save/player.json";

    public int HP;
    public float weight;
    public float length;
    public float p;//密度
    public float speed;
    public Vector2 position;

    public PlayerData() {}
    public PlayerData(Player player) {
        this.HP = player.getHP();
        this.weight = player.getWeight();
        this.length = player.getLength();
        this.p = player.getP();
        this.speed = player.getSpeed();
        this.position = player.getPosition();
    }
}
