package tokyo.ramune.savannacore.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public final class PlayerData {

    private final DBCollection players = SavannaCore.getInstance().getDatabase().getDB().getCollection("player_data");

    private final UUID uuid;
    private DBObject object = new BasicDBObject();
    private DBObject latestObject = null;
    private boolean exist = false;

    public PlayerData(@Nonnull UUID uuid) {
        this.uuid = uuid;

        final DBObject player = players.findOne(new BasicDBObject("uuid", uuid));
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
            object.put(key.getName(), key.getDef());
        }
    }

    public void save() {
        object.put("uuid", uuid);
        object.put("name", Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName());

        if (exist) {
            players.update(latestObject, object);
            latestObject = object;
        } else {
            players.insert(object);
        }
    }

    public <T> T value(@Nonnull Key key, @Nonnull Class<T> clazz) {
        return (T) object.get(key.getName());
    }

    public void value(@Nonnull Key key, Object value) {
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

        public Object getDef() {
            return def;
        }

        public String getName() {
            return name().toLowerCase();
        }
    }
}
