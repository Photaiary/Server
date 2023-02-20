package com.photaiary.Photaiary.utils.s3;

import com.photaiary.Photaiary.global.DefaultRes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpHeaders;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/s3")
@Api(tags ="S3 관련 API")
public class S3UploadTestController {

    private final S3UploadComponent s3UploadComponent;

    @ApiOperation(value = "업로드")
    @PostMapping("/upload")
    public ResponseEntity<DefaultRes> uploadFile(Long userIndex, @RequestPart("images") MultipartFile[] multipartFile) throws IOException {
        String dirName = "users/"+String.valueOf(userIndex);
        return new ResponseEntity<>(DefaultRes.res(s3UploadComponent.upload(multipartFile, dirName)), HttpStatus.OK);
    }

    @ApiOperation(value = "삭제하기")
    @DeleteMapping
    public ResponseEntity<DefaultRes> deleteFile(String filePath){ // filePath : 폴더명/파일명.파일확장자
        return new ResponseEntity<>(DefaultRes.res(s3UploadComponent.deleteFile(filePath)), HttpStatus.OK);
    }

    @ApiOperation(value = "다운로드")
    @GetMapping("/download")   // ex) http://localhost:8080/s3/download?key=users/null/2023-02-19%2016:45:27%20test.jpg
    public ResponseEntity<byte[]> downloadFile(@RequestParam String key) throws IOException {
        return s3UploadComponent.downloadFile(key);
    }

}