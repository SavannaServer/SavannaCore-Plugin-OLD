package tokyo.ramune.savannacore.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.util.UUID;

public final class PlayerData {
    private final DBCollection players = SavannaCore.getInstance().getDatabase().getPlayerData();

    private final UUID uuid;
    private DBObject object = new BasicDBObject();
    private DBObject latestObject = null;
    private boolean exist = false;

    public PlayerData(@Nonnull UUID uuid) {
        this.uuid = uuid;

        final DBObject player = players.findOne(new BasicDBObject("uuid", uuid.toString()));
        if (player == null) {
            initialize();
            return;
        }
        object = player;
        latestObject = player;
        exist = true;
    }

    public void initialize() {
        for (Key key : Key.values()) {
            object.put(key.getName(), key.getDefault());
        }
        save();
    }

    public void save() {
        object.put("uuid", uuid.toString());
        final Player player = Bukkit.getPlayer(uuid);
        object.put("name", player == null ? uuid.toString() : player.getName());

        if (exist) {
            players.update(latestObject, object);
            latestObject = object;
        } else {
            players.insert(object);
        }
    }

    public <T> T getValue(@Nonnull Key key, @Nonnull Class<T> clazz) {
        return (T) object.get(key.getName());
    }

    public void setValue(@Nonnull Key key, Object value) {
        object.put(key.getName(), value);
    }

    public enum Key {
        EXP(0),
        LEVEL(1),
        COIN(0);

        private final Object def;

        Key(Object def) {
            this.def = def;
        }

        public Object getDefault() {
            return def;
        }

        public String getName() {
            return name().toLowerCase();
        }
    }
}
