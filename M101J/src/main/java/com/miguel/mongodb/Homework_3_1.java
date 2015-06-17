package com.miguel.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Reyes
 *         Date: 6/9/15
 *         Time: 5:12 PM
 */
public class Homework_3_1 {

    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient("localhost");

        MongoDatabase schoolDatabase = client.getDatabase("school");
        MongoCollection<Document> studentsCollection = schoolDatabase.getCollection("students");
        FindIterable<Document> students = studentsCollection.find();

        for (Document student : students) {
            List<Document> scores = student.get("scores", List.class);
            Document homework1 = null;
            Document homework2;

            List<Document> newList = new ArrayList<Document>();

            boolean firstOneFound = false;
            if (scores.size() > 3) {
                for (int i = 0; i < scores.size(); i++) {
                    Document document = scores.get(i);
                    if (document.get("type").equals("homework") && !firstOneFound) {
                        firstOneFound = true;
                        homework1 = document;
                    } else {
                        if (firstOneFound) {
                            homework2 = document;
                            if (homework1.getDouble("score") > homework2.getDouble("score")) {
                                newList.add(homework1);
                            } else {
                                newList.add(homework2);
                            }
                        } else {
                            newList.add(document);
                        }
                    }
                }

                student.put("scores", newList);
                Document query = new Document("_id", student.getDouble("_id"));
                Document update = new Document("$set", new Document("scores", newList));
                studentsCollection.updateOne(query, update);
                System.out.println("Updated studentId: " + student.getDouble("_id"));
            }
        }
    }
}
