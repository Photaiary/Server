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
    @GetMapping("/download")
    public ResponseEntity<DefaultRes> downloadFile(String fileUrl) throws IOException {
        String filePath = fileUrl.substring(52);
        return new ResponseEntity<>(DefaultRes.res(s3UploadComponent.downloadFile(filePath)), HttpStatus.OK);
    }

}