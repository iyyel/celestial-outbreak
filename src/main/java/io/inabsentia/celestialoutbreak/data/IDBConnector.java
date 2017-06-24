package io.inabsentia.celestialoutbreak.data;

import org.bson.Document;

import java.util.List;

public interface IDBConnector {
    void closeDB() throws DALException;
    Document getDocument(int docId) throws DALException;
    List<Document> getDocumentList() throws DALException;
    void createDocument(Document doc) throws DALException;
    void updateDocument(Document doc) throws DALException;
    void deleteDocument(int docId) throws DALException;

    class DALException extends Exception {

        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }

    }

}