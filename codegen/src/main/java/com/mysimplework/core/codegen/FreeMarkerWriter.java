package com.mysimplework.core.codegen;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dzhao on 22/09/2015.
 */
public class FreeMarkerWriter {
    private final String templateUrl;
    private final Object input;
    private final String targetUrl;

    public FreeMarkerWriter(String templateUrl, String targetUrl, Object input){
        this.templateUrl = templateUrl;
        this.targetUrl = targetUrl;
        this.input = input;
    }

    public void write(){
        Configuration cfg = new Configuration();
        Template template = null;
        try {
            System.out.println("templateUrl: " + templateUrl);
/*

            String templatePath = getParentPath(this.getClass().getClassLoader().getSystemResource(templateUrl).getPath());
            FileTemplateLoader templateLoader = new FileTemplateLoader(new File(templatePath));
            cfg.setTemplateLoader(templateLoader);*/

            template = cfg.getTemplate(templateUrl);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        FileWriter writer = null;
        File file = new File(targetUrl);
        System.out.println("targetUrl: " + targetUrl);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            writer = new FileWriter(file);
            if (template != null) {
                try {
                    System.out.println("input: " + input.toString());
                    template.process(input, writer);
                } catch (TemplateException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static String getParentPath(String filePath){
        File file = new File(filePath);
        if(!file.exists())
            throw new RuntimeException("The file doesn't exist by [" +filePath+ "]");
        return file.getParent();
    }
}
