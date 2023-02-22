package com.photaiary.Photaiary.post.photo.controller;

import com.photaiary.Photaiary.post.photo.controller.exception.custom.VoException;
import com.photaiary.Photaiary.post.photo.dto.*;
import com.photaiary.Photaiary.post.photo.service.PhotoService;
import com.photaiary.Photaiary.post.photo.vo.BucketVo;
import com.photaiary.Photaiary.post.photo.vo.PhotoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;

    @PutMapping("/edit")
    public ResponseEntity<PhotoSimpleResponse> update(@RequestBody EditRequest editRequest) throws VoException {
        return new ResponseEntity(PhotoSimpleResponse
                .builder()
                .isSuccuess(photoService.photoEdit(editRequest))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<PhotoResponse> upload(@RequestParam("img") MultipartFile img, @RequestPart PhotoRequest photoRequest) throws Exception {
        PhotoVo photoVo = new PhotoVo(img);
        BucketVo bucketVo = new BucketVo(photoService.s3Upload(img));
        PhotoResponse photoResponse = PhotoResponse.builder()
                .photoIndex(photoService.photoInfoSave(photoRequest, photoVo, bucketVo))
                .longitude(photoVo.getLongitude())
                .latitude(photoVo.getLatitude())
                .build();
        return new ResponseEntity(photoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<PhotoSimpleResponse> delete(@RequestBody SinglePhotoDto singlePhotoDto) throws VoException {
        return new ResponseEntity(PhotoSimpleResponse
                .builder()
                .isSuccuess(photoService.photoDelete(singlePhotoDto))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<SinglePhotoResponse> viewOnePhoto(@RequestBody SinglePhotoDto singlePhotoDto) throws VoException{
        return new ResponseEntity(photoService.viewSinglePhoto(singlePhotoDto), HttpStatus.OK);
    }
}


