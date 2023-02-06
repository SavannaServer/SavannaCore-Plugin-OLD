package tokyo.ramune.savannacore.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.util.UUID;

public final class GlobalScore {
    private final DBCollection scores = SavannaCore.getInstance().getDatabase().getScore();

    private final UUID playerUniqueId;
    private DBObject object = new BasicDBObject();
    private DBObject latestObject = null;
    private boolean exist = false;

    public GlobalScore(@Nonnull UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;

        final DBObject score = scores.findOne(new BasicDBObject("uuid", playerUniqueId));
        if (score == null) {
            initialize();
            return;
        }
        object = score;
        latestObject = score;
        exist = true;
    }

    public void initialize() {
        for (GlobalScore.Key key : GlobalScore.Key.values()) {
            object.put(key.getName(), key.getDefault());
        }
        save();
    }

    public void save() {
        object.put("uuid", playerUniqueId);
        final Player player = Bukkit.getPlayer(playerUniqueId);
        object.put("name", player == null ? playerUniqueId.toString() : player.getName());

        if (exist) {
            scores.update(latestObject, object);
            latestObject = object;
        } else {
            scores.insert(object);
        }
    }

    public <T> T getValue(@Nonnull GlobalScore.Key key, @Nonnull Class<T> clazz) {
        return (T) object.get(key.getName());
    }

    public void setValue(@Nonnull GlobalScore.Key key, Object value) {
        object.put(key.getName(), value);
    }

    public enum Key {
        SCORE(0),

        KILLS(0),
        DEATHS(0),

        HITS(0),
        HEADSHOTS(0),

        WINS(0),
        DEFEATS(0);

        private final long def;

        Key(long def) {
            this.def = def;
        }

        public long getDefault() {
            return def;
        }

        public String getName() {
            return name().toLowerCase();
        }
    }
}
