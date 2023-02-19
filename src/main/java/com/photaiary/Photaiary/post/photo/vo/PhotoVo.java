package com.photaiary.Photaiary.post.photo.vo;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.FileConvertException;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.NoLocationException;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.VoException;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Getter
@NoArgsConstructor
@ToString
public class PhotoVo {
    public PhotoVo(MultipartFile multipartFile) throws VoException {
        try {
            updateLocation(multiFileToFile(multipartFile));
        } catch (NoLocationException e) {
            throw new VoException(e.getMessage());
        } catch (FileConvertException e) {
            throw new VoException(e.getMessage());
        }
    }

    private String latitude;
    private String longitude;

    private File multiFileToFile(MultipartFile multipartFile) throws FileConvertException {
        try {
            File convFile = new File(multipartFile.getOriginalFilename());
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            return convFile;
        } catch (IOException e) {
            throw new FileConvertException("파일 변환에 실패하였습니다. 사유 : " + e.getMessage());
        }
    }

    private void updateLocation(@NotNull File file) throws NoLocationException {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

            latitude = String.format("%.2f", gpsDirectory.getGeoLocation().getLatitude());
            longitude = String.format("%.2f", gpsDirectory.getGeoLocation().getLongitude());
        } catch (IOException e) {
            throw new NoLocationException("파일 입출력에 실패하였습니다. 사유 : " + e.getMessage());
        } catch (ImageProcessingException e) {
            throw new NoLocationException("GPS 정보가 존재하지 않습니다. 사유 : " + e.getMessage());
        }
    }
}
