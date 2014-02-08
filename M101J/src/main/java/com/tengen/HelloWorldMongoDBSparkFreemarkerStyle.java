package com.tengen;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;

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

        final DBCollection collection = getDbCollection();

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {
                LOG.debug("Handling all requests to /");
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    DBObject document = collection.findOne();
                    helloTemplate.process(document, writer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }

    private static DBCollection getDbCollection() {
        MongoClient client = null;
        try {
            client = new MongoClient(new ServerAddress("localhost"));
            LOG.debug("Connected to MongoDB");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DB test = client.getDB("test");
        return test.getCollection("things");
    }


}
