package com.miguel.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static java.util.Arrays.asList;

/**
 * @author Miguel Reyes
 *         Date: 7/6/15
 *         Time: 6:34 PM
 */
public class ReplicaSet {

    public static void main(String[] args) throws InterruptedException {

        MongoClient client = new MongoClient(asList(
                new ServerAddress("LOMICOSX006.local:27017"),
                new ServerAddress("LOMICOSX006.local:27018"),
                new ServerAddress("LOMICOSX006.local:27019")),
                MongoClientOptions.builder().requiredReplicaSetName("rs1").build());

        MongoCollection<Document> collection = client.getDatabase("test").getCollection("numbers");
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            collection.insertOne(new Document("_id", i));
            Thread.sleep(500);
        }
    }
}
