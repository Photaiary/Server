package com.photaiary.Photaiary.post.photo.controller;

import com.photaiary.Photaiary.post.photo.dto.SinglePhotoDto;
import com.photaiary.Photaiary.post.photo.dto.EditRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoS3Dto;
import com.photaiary.Photaiary.post.photo.service.PhotoService;
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

    @PostMapping("/upload")
    public Long upload(@RequestParam("img") MultipartFile img, @RequestPart PhotoRequest photoRequest) throws Exception {
        PhotoVo photoVo = new PhotoVo(img);
        PhotoS3Dto photoS3Dto = new PhotoS3Dto();
        return photoService.photoInfoSave(photoRequest, photoVo, photoS3Dto);
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

