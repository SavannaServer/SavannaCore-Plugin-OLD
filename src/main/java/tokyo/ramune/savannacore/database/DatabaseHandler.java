package tokyo.ramune.savannacore.database;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import javax.annotation.Nonnull;

public class DatabaseHandler {
    private MongoClient client;

    public DatabaseHandler() {
    }

    public void connect(@Nonnull String host, int port) {
        if (client != null) client.close();
        try {
            client = new MongoClient(host, port);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public MongoClient getClient() {
        return client;
    }

    public DB getDB() {
        return client.getDB("savanna");
    }
}
