package com.test.alipay.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class AliOssUtil {

    // TODO 参数自己配置
    private static OSSClient OSS_CLIENT = new OSSClient("endpoint",
            "accessKey", "accessSecret");
    public static OSSClient getOSSClient() {
        return new OSSClient("endpoint", "accessKey", "accessSecret");
    }

    public static String createBucketName(OSSClient ossClient, String bucketName) {
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建存储空间
            Bucket bucket = ossClient.createBucket(bucketName);
            return bucket.getName();
        }
        return bucketNames;
    }

    public static void deleteBucket(OSSClient ossClient, String bucketName) {
        ossClient.deleteBucket(bucketName);
    }

    public static String createFolder(OSSClient ossClient, String bucketName, String folder) {//文件夹名   
        final String keySuffixWithSlash = folder;
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
    }

    public static String uploadObject2OSS(OSSClient ossClient, MultipartFile file, String bucketName, String folder) {
        String resultStr = null;
        String fileName = "";
        try {
            if (!StringUtils.isBlank(file.getOriginalFilename())) {
                fileName = file.getOriginalFilename();
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(UUID.randomUUID().toString());
                stringBuilder.append(System.currentTimeMillis());
                stringBuilder.append(".png");
                fileName = stringBuilder.toString();
            }
            Long fileSize = file.getSize();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileSize);
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件
            ossClient.putObject(bucketName, folder + fileName, file.getInputStream(), metadata);
            resultStr = "http://bucketName.endpoint/folder/fileName";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    public static String getContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        return fileName;
    }

    /**
     * @param headPic
     * @param folder
     * @return
     */
    public static String uploadImg(MultipartFile headPic, String folder) {
        OSSClient ossClient = getOSSClient();
        return uploadObject2OSS(ossClient, headPic, "bucketName", folder);
    }

}
