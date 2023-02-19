package com.photaiary.Photaiary.utils.s3;

import com.photaiary.Photaiary.global.DefaultRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class S3UploadTestController {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<DefaultRes> uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        String file = s3UploadService.upload(multipartFile);
        return new ResponseEntity<>(DefaultRes.res(file), HttpStatus.OK);
    }

}