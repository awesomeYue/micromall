package com.mmall.services.impl;

import com.google.common.collect.Lists;
import com.mmall.services.FileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("fileService")
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String upload(MultipartFile file, String path) {
        String filename = file.getOriginalFilename();
        //获取扩展名
        String fileExtensionName = filename.substring(filename.lastIndexOf("."));
        String uploadFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExtensionName;
        logger.info("文件开始上传,上传文件的文件名:{},上传文件的路径:{},新的文件名:{}", filename, path, fileExtensionName);
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            //将targetFile上传到ftp服务器上
            boolean  result = FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //上传完成后，删除upload下面的文件
            if (result) {
                targetFile.delete();
            }
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

}
