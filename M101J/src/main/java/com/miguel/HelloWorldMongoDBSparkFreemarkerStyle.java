package com.miguel;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;

/**
 * Dazoo project
 *
 * @author Miguel Reyes
 *         Date: 14/01/14
 *         Time: 19:00
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldMongoDBSparkFreemarkerStyle.class);

    public static void main(String[] args) {

        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        final MongoCollection<BasicDBObject> collection = getDbCollection();

        Spark.get("/", new Route() {
            @Override
            public Object handle(final Request request, final Response response) {
                LOG.debug("Handling all requests to /");
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    BasicDBObject document = collection.find().first();
                    helloTemplate.process(document, writer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }

    private static MongoCollection<BasicDBObject> getDbCollection() {
        MongoClient client = null;
        client = new MongoClient("localhost");
        LOG.debug("Connected to MongoDB");

        MongoDatabase test = client.getDatabase("test");
        return test.getCollection("things", BasicDBObject.class);
    }


}
