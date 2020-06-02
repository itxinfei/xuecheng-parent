package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
public class FileSystemService {

    @Value("${xuecheng.fastdfs.tracker_servers}")
    String tracker_servers;

    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    int connect_timeout_in_seconds;

    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    int network_timeout_in_seconds;

    @Value("${xuecheng.fastdfs.charset}")
    String charset;

    @Resource
    FileSystemRepository fileSystemRepository;

    public UploadFileResult upload(MultipartFile file,
                                   String filetag,
                                   String businesskey,
                                   String metadata) {
        if (file == null) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //上传文件到fdfs
        String fileId = this.fdfs_upload(file);
        if (StringUtils.isEmpty(fileId)) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_BUSINESSISNULL);
        }
        FileSystem fileSystem = new FileSystem();
        fileSystem.setFileId(fileId);
        fileSystem.setFilePath(fileId);
        fileSystem.setBusinesskey(businesskey);
        fileSystem.setFiletag(filetag);

        if (StringUtils.isNotEmpty(metadata)) {
            Map map = JSON.parseObject(metadata, Map.class);
            fileSystem.setMetadata(map);
        }

        fileSystem.setFileName(file.getOriginalFilename());
        //大小
        fileSystem.setFileSize(file.getSize());
        // 文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);

        return new UploadFileResult(CommonCode.SUCCESS, fileSystem);

    }

    //上传文件到fdfs，返回文件id
    public String fdfs_upload(MultipartFile file) {
        //初始化
        initFdfsConfig();
        //创建tracker client
        TrackerClient trackerClient = new TrackerClient();
        //获取trackerServer
        try {
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            //创建storage client
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            //上传文件
            // 文件字节
            byte[] bytes = file.getBytes();
            // 文件原始名称
            String originalFilename = file.getOriginalFilename();
            //文件扩展名
            String extname = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //文件id
            String file1 = storageClient1.upload_file1(bytes, extname, null);
            System.out.println("返回的文件id："+file1);
            return file1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //加载fdfs的配置
    private void initFdfsConfig() {

        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
            System.out.println("加载加载fdfs的配置-成功！");
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }


    }


}
