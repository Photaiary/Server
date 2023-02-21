package com.photaiary.Photaiary.post.photo.controller;

import com.photaiary.Photaiary.post.photo.dto.PhotoResponse;
import com.photaiary.Photaiary.post.photo.dto.SinglePhotoDto;
import com.photaiary.Photaiary.post.photo.dto.EditRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.service.PhotoService;
import com.photaiary.Photaiary.post.photo.vo.BucketVo;
import com.photaiary.Photaiary.post.photo.vo.PhotoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;

    @PutMapping("/edit")
    public Long update(@RequestBody EditRequest editRequest) {
        return photoService.photoEdit(editRequest);
    }

    @ResponseBody
    @PostMapping("/upload")
    public PhotoResponse upload(@RequestParam("img") MultipartFile img, @RequestPart PhotoRequest photoRequest) throws Exception {
        PhotoVo photoVo = new PhotoVo(img);
        BucketVo bucketVo = new BucketVo(photoService.s3Upload(img));
        PhotoResponse photoResponse = PhotoResponse.builder()
                .photoIndex(photoService.photoInfoSave(photoRequest, photoVo, bucketVo))
                .longitude(photoVo.getLongitude())
                .latitude(photoVo.getLatitude())
                .build();
        return photoResponse;
    }

    @DeleteMapping("/")
    public Boolean delete(@RequestBody SinglePhotoDto singlePhotoDto) {
        return photoService.photoDelete(singlePhotoDto);
    }

    @GetMapping("/")
    public String viewOnePhoto(@RequestBody SinglePhotoDto singlePhotoDto) {
        return photoService.viewSinglePhoto(singlePhotoDto);
    }
    @GetMapping("/upload")
    public String yes() {
        return "yes";
    }
}


