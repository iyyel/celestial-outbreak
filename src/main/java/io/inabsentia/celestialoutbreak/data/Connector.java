package io.inabsentia.celestialoutbreak.data;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.utils.GameUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Connector implements IConnector {

    private static Connector instance;

    private final GameUtils gameUtils = GameUtils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDb;
    private final MongoCollection collection;

    private String dbUrl, dbName, dbCollection;
    private int dbPort;

    private Connector() {
        initDbProperties();
        ServerAddress serverAddress = new ServerAddress(dbUrl, dbPort);
        mongoClient = new MongoClient(serverAddress, getAuthList());
        mongoDb = mongoClient.getDatabase(dbName);
        collection = mongoDb.getCollection(dbCollection);
    }

    static {
        try {
            instance = new Connector();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Connector getInstance() {
        return instance;
    }

    private void initDbProperties() {
        String dbConfigFilePath = FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.DATABASE_CONFIG_FILE_NAME).getPath();
        Map<String, String> map = fileHandler.readPropertiesFromFile(dbConfigFilePath);
        this.dbUrl = map.get("DB_URL");
        this.dbPort = Integer.parseInt(map.get("DB_PORT"));
        this.dbName = map.get("DB_NAME");
        this.dbCollection = map.get("DB_COLLECTION");
    }

    private List<MongoCredential> getAuthList() {
        List<MongoCredential> dbAuths = new ArrayList<>();
        MongoCredential adminAuth = MongoCredential.createPlainCredential("admin", "admin", "testpass".toCharArray());
        //MongoCredential coAuth = MongoCredential.createPlainCredential("co-admin", "co-gamedb", "testpass".toCharArray());
        dbAuths.add(adminAuth);
        return dbAuths;
    }

    @Override
    public void close() throws DALException {
        try {
            mongoClient.close();
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg("Successfully closed database connection.");
        } catch (Exception e) {
            throw new DALException(e.getMessage(), e);
        }
    }

    @Override
    public Document getDocument(int docId) throws DALException {
        BasicDBObject query = new BasicDBObject("_id", docId);
        MongoCursor<Document> cursor = collection.find(query).iterator();

        if (!cursor.hasNext())
            throw new DALException("No document with [" + docId + "] exist in collection [" + collection.getNamespace().getFullName() + "]");

        return cursor.next();
    }

    @Override
    public List<Document> getDocumentList() throws DALException {
        List<Document> docList = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        if (!cursor.hasNext())
            throw new DALException("No entries in collection [" + collection.getNamespace().getFullName() + "]");

        while (cursor.hasNext()) docList.add(cursor.next());

        return docList;
    }

    @Override
    public void createDocument(Document doc) throws DALException {
        try {
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new DALException(e.getMessage(), e);
        }
    }

    @Override
    public void updateDocument(Document doc) throws DALException {
        try {
            BasicDBObject query = new BasicDBObject("_id", doc.getInteger("_id"));
            BasicDBObject updateObj = new BasicDBObject("$set", doc);
            collection.updateOne(query, updateObj);
        } catch (Exception e) {
            throw new DALException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteDocument(int docId) throws DALException {
        try {
            BasicDBObject query = new BasicDBObject("_id", docId);
            collection.deleteOne(query);
        } catch (Exception e) {
            throw new DALException(e.getMessage(), e);
        }
    }

}