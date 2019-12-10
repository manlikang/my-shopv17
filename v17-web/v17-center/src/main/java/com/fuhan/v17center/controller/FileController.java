package com.fuhan.v17center.controller;

import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.v17center.pojo.WangEditorResultBean;
import com.fuhan.v17center.resource.Resource;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/1
 */
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Autowired
    private FastFileStorageClient client;
    @Autowired
    private Resource resource;
    @PostMapping("upload")
    public ResultBean<String> upload(MultipartFile file_upload){
        String filename = file_upload.getOriginalFilename();
        String extm = filename.substring(filename.lastIndexOf(".")+1);
        try {
            StringBuilder stringBuilder = new StringBuilder(resource.getImageServer());
            StorePath storePath = client.uploadFile(file_upload.getInputStream(), file_upload.getSize(), extm, null);
            String fullpath = stringBuilder.append(storePath.getFullPath()).toString();
            System.out.println(fullpath);
            return new ResultBean<>("200",fullpath);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResultBean<>("500","服务器繁忙请稍后再试");
        }
    }


    @PostMapping("batchUpload")
    @ResponseBody
    public WangEditorResultBean batchUpload(MultipartFile[] files){
        String[] data = new String[files.length];
        try {
            for(int i=0;i<files.length;i++){
                String fileName = files[i].getOriginalFilename();
                String extm=fileName.substring(fileName.lastIndexOf(".")+1);
                StringBuilder stringBuilder = new StringBuilder(resource.getImageServer());
                StorePath storePath = client.uploadFile(files[i].getInputStream(), files[i].getSize(), extm, null);
                String fileFullPath = stringBuilder.append(storePath.getFullPath()).toString();
                data[i]=fileFullPath;
            }
            System.out.println("上传成功");
            for (String path : data) {
                System.out.println(path);
            }
            return new WangEditorResultBean(0,data);
        }catch (IOException e) {
            log.error(e.getMessage());
            return new WangEditorResultBean(-1,null);
        }

    }


}
