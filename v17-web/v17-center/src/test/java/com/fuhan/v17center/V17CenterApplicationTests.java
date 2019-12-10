package com.fuhan.v17center;

import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.v17center.sender.Sender;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
public class V17CenterApplicationTests {

    @Autowired
    private FastFileStorageClient client;
    @Autowired
    private Sender sender;

    @Test
    void contextLoads() {
        sender.send2("你妈死了","ttl2");
    }
    @Test
    public void uploadTest()  {
        try {
        File file = new File("D:\\IDEA_workspace\\javaWeb\\shopv17\\v17-web\\v17-center\\src\\main\\resources\\templates\\product\\index.html");
        FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = client.uploadFile(inputStream, file.length(), "html", null);
            String fullpath = storePath.getFullPath();
            System.out.println(fullpath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
