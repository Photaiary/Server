package com.photaiary.Photaiary.post.photo.vo;

import com.photaiary.Photaiary.utils.s3.S3UploadComponent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@NoArgsConstructor
@Getter
public class BucketVo {

    private String imageLink;

    public BucketVo(String imageLink) {
        this.imageLink = imageLink.replace("%2F%2F%2F","//");
    }
}
