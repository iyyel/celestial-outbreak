package io.inabsentia.celestialoutbreak.data;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
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

public class DBConnector implements IDBConnector {

    private static DBConnector instance;

    private final GameUtils gameUtils = GameUtils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final MongoCollection collection;

    private String dbUrl, dbName, dbCollection;

    private DBConnector() {
        initDatabaseProperties();
        mongoClient = new MongoClient(new MongoClientURI(dbUrl));
        mongoDatabase = mongoClient.getDatabase(dbName);
        collection = mongoDatabase.getCollection(dbCollection);
    }

    static {
        try {
            instance = new DBConnector();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DBConnector getInstance() {
        return instance;
    }

    private void initDatabaseProperties() {
        String dbConfigFilePath = FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.DATABASE_CONFIG_FILE_NAME).getPath();
        Map<String, String> map = fileHandler.readPropertiesFromFile(dbConfigFilePath);
        this.dbUrl = map.get("DATABASE_URL");
        this.dbName = map.get("DATABASE_NAME");
        this.dbCollection = map.get("DATABASE_COLLECTION");
    }

    @Override
    public void closeDB() throws DALException {
        try {
            mongoClient.close();
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMessage("Successfully closed database connection.");
        } catch (Exception e) {
            throw new DALException(e.getMessage(), e);
        }
    }

    @Override
    public Document getDocument(int docId) throws DALException {
        BasicDBObject query = new BasicDBObject("_id", docId);
        MongoCursor<Document> cursor = collection.find(query).iterator();

        if (!cursor.hasNext()) throw new DALException("No document with [" + docId + "] exist in collection [" + collection.getNamespace().getFullName() + "]");

        return cursor.next();
    }

    @Override
    public List<Document> getDocumentList() throws DALException {
        List<Document> docList = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        if (!cursor.hasNext()) throw new DALException("No entries in collection [" + collection.getNamespace().getFullName() + "]");

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