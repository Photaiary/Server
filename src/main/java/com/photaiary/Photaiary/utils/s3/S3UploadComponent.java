package com.photaiary.Photaiary.utils.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class S3UploadComponent {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public List<String> upload(MultipartFile[] multipartFile, String dirName) throws IOException {
        // 파일 리스트 하나씩 업로드
        List<String> listUrl = new ArrayList<>();
        for(MultipartFile mf :multipartFile){
            File uploadFile = convert(mf)
                    .orElseThrow(()-> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            // 파일 이름 중복되지 않기 위해서 UUID로 생성한 랜덤 값과 파일 이름을 연결하여 S3에 업로드
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy-MM-dd HH:mm:ss"));
            String fileName = dirName + formatDate + " " + mf.getOriginalFilename();

            // s3 업로드
            String uploadImageUrl = putS3(uploadFile, fileName);
            // 로컬 파일 삭제
            removeFile(uploadFile);

            listUrl.add(uploadImageUrl);
        }
        return listUrl;
    }
    public String uploadOneFile(MultipartFile multipartFile, String dirName) throws IOException {
            File uploadFile = convert(multipartFile)
                    .orElseThrow(()-> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

            // 파일 이름 중복되지 않기 위해서 UUID로 생성한 랜덤 값과 파일 이름을 연결하여 S3에 업로드
            String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("/yyyy-MM-dd HH:mm:ss"));
            String fileName = dirName + formatDate + " " + multipartFile.getOriginalFilename();

            // s3 업로드
            String uploadImageUrl = putS3(uploadFile, fileName);
            // 로컬 파일 삭제
            removeFile(uploadFile);
        return uploadImageUrl;
    }

    // 파일 전환
    private Optional<File> convert(MultipartFile file) throws IOException{
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return Optional.of(convFile);
    }

    // s3 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    // 로컬 파일 삭제
    private void removeFile(File uploadFile) {
        if(uploadFile.exists()){
            if(uploadFile.delete()) {
                log.info("파일이 삭제되었습니다.");
            }else{
                log.info("파일이 삭제되지 못했습니다.");
            }
        }
    }

    public Object deleteFile(String filePath) {
        try{
            // s3 에서 삭제
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, filePath));

        }catch (AmazonServiceException e){
            e.printStackTrace();
        }catch (SdkClientException e){
            e.printStackTrace();
        }
        return "삭제 성공";
    }

   public ResponseEntity<byte[]> downloadFile(String key) throws IOException {
       GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
       S3Object s3Object = amazonS3.getObject(getObjectRequest);
       S3ObjectInputStream objectInputStream = s3Object.getObjectContent();
       byte[] bytes = IOUtils.toByteArray(objectInputStream);
       String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

       HttpHeaders httpHeaders = new HttpHeaders();
       httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       httpHeaders.setContentLength(bytes.length);
       httpHeaders.setContentDispositionFormData("attachment", fileName);

       return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }
}