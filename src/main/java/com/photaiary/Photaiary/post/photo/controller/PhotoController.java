package com.photaiary.Photaiary.post.photo.controller;

import com.photaiary.Photaiary.post.photo.dto.EditRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoS3Dto;
import com.photaiary.Photaiary.post.photo.service.PhotoService;
import com.photaiary.Photaiary.post.photo.vo.PhotoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;
    
//    @PostMapping("/upload")
//    public Long upload(@RequestBody PhotoRequest photoRequest){
//        return photoService.photoInfoSave(photoRequest);
//    }

    @PutMapping("/edit")
    public Long update(@RequestBody EditRequest editRequest) {
        return photoService.photoEdit(editRequest);
    }

    @PostMapping("/uploadtest")
    public Long uploadTest(@RequestParam("img") MultipartFile img, @RequestPart PhotoRequest photoRequest) throws Exception {
        PhotoVo photoVo = new PhotoVo(img);
        PhotoS3Dto photoS3Dto = new PhotoS3Dto();
        return photoService.photoInfoSave(photoRequest, photoVo, photoS3Dto);
    }

    @GetMapping("/upload")
    public String yes() {
        return "yes";
    }
}


