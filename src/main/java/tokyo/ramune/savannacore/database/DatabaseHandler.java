package tokyo.ramune.savannacore.database;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import javax.annotation.Nonnull;

public class DatabaseHandler {
    private MongoClient client;
    private DBCollection playerData, score;

    public DatabaseHandler() {
    }

    public void connect(@Nonnull String host, int port) {
        if (client != null) client.close();
        try {
            client = new MongoClient(host, port);
            setCollections();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setCollections() {
        playerData = getDB().getCollection("player_data");
        score = getDB().getCollection("score");
    }

    public MongoClient getClient() {
        return client;
    }

    public DB getDB() {
        return client.getDB("savanna");
    }

    public DBCollection getPlayerData() {
        return playerData;
    }

    public DBCollection getScore() {
        return score;
    }
}
