package com.tengen;

import com.mongodb.*;

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

        MongoClient client = new MongoClient(new ServerAddress("localhost"));

        DB test = client.getDB("test");
        DBCollection things = test.getCollection("things");

        DBObject document = things.findOne();
        System.out.println(document);

    }

}
