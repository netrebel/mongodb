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

        Document projection = new Document()
                .append("student_id", 1);

        FindIterable<Document> students = studentsCollection.find();

        for (Document student : students) {
            ArrayList<Document> scores = student.get("scores", ArrayList.class);
            Document homework1 = scores.get(2);
            Document homework2 = scores.get(3);

            List<Document> newList = new ArrayList<Document>();
            newList.add(scores.get(0));
            newList.add(scores.get(1));

            if (homework1.getDouble("score") > homework2.getDouble("score")) {
                newList.add(homework1);
            } else {
                newList.add(homework2);
            }

            student.put("scores", newList);
            Document query = new Document("_id", student.getDouble("_id"));
            Document update = new Document("$set", new Document("scores", newList));
            studentsCollection.updateOne(query, update);

            /*java.util.List<Document> newScores = new ArrayList<Document>();
            for (int i = 0; i < scores.size(); i++) {
                Document document = scores.get(i);
                if (document.get("type").equals("homework")) {
                    score = document.getDouble("score");
                } else {
                    newScores.add(document);
                    newScores.add(document);
                }
            }*/
            System.out.println("Found studentId: " + student.getDouble("_id"));

        }


//                studentsCollection.deleteOne(first);


    }
}
