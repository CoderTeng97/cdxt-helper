package com.cdxt.backend.controller;

import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.base.BaseController;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.UploadFileResponseVO;
import com.cdxt.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/file")
@ControllerResponseProcessor
public class FileController extends BaseController {

    @Value("${file.upload.path}")
    private  String fileUploadUri;
    @Value("${file.download.uri}")
    private  String fileDownloadUri;


    @Autowired
    IdWorker idWorker;


    @PostMapping("/upload")
    public UploadFileResponseVO  uploadFile(@RequestParam("file") MultipartFile file){
        log.info("文件进行上传" + file.getOriginalFilename());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = idWorker.nextId() + "."  + suffix;
        try {
            Path targetLocation = Paths.get(fileUploadUri).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("文件上传失败",e);
            throw new ResponseCommonException(HttpStatus.BAD_REQUEST,"保存文件 " + fileName + "失败，请稍后重试");
        }
       return new UploadFileResponseVO(fileName, fileDownloadUri + fileName,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/upload/multi")
        public List<UploadFileResponseVO> UploadFileResponseVO(@RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponseVO> fileResponseVOS = Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
        return fileResponseVOS;
    }


}
