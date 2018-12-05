package com.test.alipay.controller;

import com.test.alipay.constants.AliOssConstants;
import com.test.alipay.util.AliOssUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/aliOss")
public class AliOssController {

    @RequestMapping("/upload")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file){
        return AliOssUtil.uploadImg(file, AliOssConstants.filedir);
    }

}
