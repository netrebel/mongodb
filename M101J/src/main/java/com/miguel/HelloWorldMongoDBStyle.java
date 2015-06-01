package com.miguel;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;

/**
 * Dazoo project
 *
 * @author Miguel Reyes
 *         Date: 14/01/14
 *         Time: 18:19
 */
public class HelloWorldMongoDBStyle {

    public static void main(String[] args) throws UnknownHostException {

        MongoClient client = new MongoClient("localhost");

        MongoDatabase test = client.getDatabase("test");
        MongoCollection<BasicDBObject> things = test.getCollection("things", BasicDBObject.class);

        BasicDBObject first = things.find().first();
        System.out.println(first);

    }

}
