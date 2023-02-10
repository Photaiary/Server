package com.photaiary.Photaiary.post.photo.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageVo {
    private MultipartFile file;
    private String latitude;
    private String longitude;
    public boolean hasLocation(){
        return longitude.length() != 0 && latitude.length() != 0;
    }
    public boolean isFileNull() {
        return file == null;
    }
    public List<String> location() {
        return List.of(latitude, longitude);
    }
    @Builder
    public ImageVo(MultipartFile multipartFile, String latitude, String longitude) {
        this.file = multipartFile;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
