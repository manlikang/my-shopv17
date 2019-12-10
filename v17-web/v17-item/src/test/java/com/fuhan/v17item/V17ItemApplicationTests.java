package com.fuhan.v17item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class V17ItemApplicationTests {


    @Autowired
    private Configuration configuration;


    @Test
    void contextLoads() {
    }

    @Test
    public void createHTMLTest() throws IOException, TemplateException {
        Template template =configuration.getTemplate("freemarker.ftl");

        Map<String,Object> data = new HashMap<>();
        data.put("msg","fuhan");
        String serverpath= ResourceUtils.getURL("classpath:static").getPath();
        StringBuilder builder = new StringBuilder(serverpath).append(File.separator+"freemarker.html");
        FileWriter out = new FileWriter(builder.toString());
        template.process(data,out);
        System.out.println("success!!!");
    }

}
