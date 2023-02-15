package tokyo.ramune.savannacore.data;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tokyo.ramune.savannacore.SavannaCore;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public final class GlobalScore {
    private final DBCollection scores = SavannaCore.getInstance().getDatabase().getScore();

    private final UUID playerUniqueId;
    private DBObject object = new BasicDBObject();
    private DBObject latestObject = null;
    private boolean exist = false;

    public GlobalScore(@Nonnull UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;

        final DBObject score = scores.findOne(new BasicDBObject("uuid", playerUniqueId.toString()));
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
        object.put("uuid", playerUniqueId.toString());
        final Player player = Bukkit.getPlayer(playerUniqueId);
        object.put("name", player == null ? playerUniqueId.toString() : player.getName());

        if (exist) {
            scores.update(latestObject, object);
            latestObject = object;
        } else {
            scores.insert(object);
        }
    }

    public <T> T getValue(@Nonnull GlobalScore.Key<T> key) {
        return (T) object.get(key.getName());
    }

    public <T> void setValue(@Nonnull GlobalScore.Key<T> key, T value) {
        object.put(key.getName(), value);
    }

    public interface Key<T> {
        Key<Long> SCORE = new Key<>() {
            @Override
            public String getName() {
                return "score";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> PLAYTIME = new Key<>() {
            @Override
            public String getName() {
                return "playtime";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> KILLS = new Key<>() {
            @Override
            public String getName() {
                return "kills";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> DEATHS = new Key<>() {
            @Override
            public String getName() {
                return "deaths";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> HITS = new Key<>() {
            @Override
            public String getName() {
                return "hits";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> HEADSHOTS = new Key<>() {
            @Override
            public String getName() {
                return "headshots";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> WINS = new Key<>() {
            @Override
            public String getName() {
                return "wins";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };
        Key<Long> DEFEATS = new Key<>() {
            @Override
            public String getName() {
                return "defeats";
            }

            @Override
            public Long getDefault() {
                return 0L;
            }

            @Override
            public Long add(Long t1, Long t2) {
                return t1 + t2;
            }
        };

        static Collection<Key> values() {
            return Set.of(
                    SCORE,
                    PLAYTIME,
                    KILLS,
                    DEATHS,
                    HITS,
                    HEADSHOTS,
                    WINS,
                    DEFEATS
            );
        }

        String getName();

        T getDefault();

        T add(T t1, T t2);
    }
}
