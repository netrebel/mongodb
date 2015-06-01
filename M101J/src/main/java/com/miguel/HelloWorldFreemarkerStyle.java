package com.miguel;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Miguel Reyes
 *         Date: 14/01/14
 *         Time: 18:43
 */
public class HelloWorldFreemarkerStyle {

    public static void main(String[] args) {
            Configuration configuration = new Configuration();
            configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        try {
            Template helloTemplate = configuration.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> helloMap = new HashMap<String, Object>();
            helloMap.put("name", "Freemarker");

            helloTemplate.process(helloMap, writer);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
