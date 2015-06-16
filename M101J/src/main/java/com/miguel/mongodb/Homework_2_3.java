package com.miguel.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import java.net.UnknownHostException;

/**
 * @author Miguel Reyes
 *         Date: 6/9/15
 *         Time: 5:12 PM
 */
public class Homework_2_3 {

    public static void main(String[] args) throws UnknownHostException {

            MongoClient client = new MongoClient("localhost");

            MongoDatabase test = client.getDatabase("students");
            MongoCollection<Document> gradesCollection = test.getCollection("grades");

            Document projection = new Document()
                    .append("student_id", 1);

            DistinctIterable<Double> student_id = gradesCollection.distinct("student_id", Double.class);
            for (double studentId : student_id) {
                System.out.println("Student with studentId: " + studentId);
                Document query = new Document()
                        .append("student_id", studentId)
                        .append("type", "homework");

                Document first = gradesCollection.find(query)
                        .sort(Sorts.ascending("student_id", "score"))
//                        .projection(Projections.fields(projection))
                        .first();

                System.out.println("Delete studentId: " + first.getDouble("student_id") + " and score " + first.getDouble("score"));

//                gradesCollection.deleteOne(first);

            }


        }
}
