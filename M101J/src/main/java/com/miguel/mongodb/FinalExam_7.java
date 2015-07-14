package com.miguel.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * @author Miguel Reyes
 *         Date: 7/14/15
 *         Time: 3:36 PM
 */
public class FinalExam_7 {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase database = mongoClient.getDatabase("test");

        MongoCollection<Document> imagesCollection = database.getCollection("images");
        final MongoCollection<Document> albumsCollection = database.getCollection("albums");

        List<Document> images = imagesCollection.find().into(new ArrayList<>());

        images.stream()
                .forEach(image -> {
                    Integer id = image.getInteger("_id");
                    Document album = albumsCollection.find(eq("images", id)).first();
                    if (album == null) {
                        imagesCollection.deleteOne(eq("_id", id));
                        System.out.println("Deleted image with id: " + id);
                    }
                });

    }

}
