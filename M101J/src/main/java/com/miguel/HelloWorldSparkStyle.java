package com.miguel;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Dazoo project
 *
 * @author Miguel Reyes
 *         Date: 14/01/14
 *         Time: 18:38
 */
public class HelloWorldSparkStyle {

    public static void main(String[] args) {
        Spark.get("/", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "Hello World from Spark";
            }
        });
    }
}
