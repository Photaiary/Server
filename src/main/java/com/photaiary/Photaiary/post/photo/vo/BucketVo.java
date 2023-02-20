package com.photaiary.Photaiary.post.photo.vo;

import com.photaiary.Photaiary.utils.s3.S3UploadComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@NoArgsConstructor
@Getter
@ComponentScan
public class BucketVo {
    private S3UploadComponent s3UploadComponent;
    private String imageLink;

    public BucketVo(MultipartFile multipartFile) throws IOException {
        this.imageLink = s3UploadComponent.uploadOneFile(multipartFile, "/");
    }
}
