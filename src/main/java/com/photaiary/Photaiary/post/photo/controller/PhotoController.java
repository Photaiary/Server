package com.photaiary.Photaiary.post.photo.controller;

import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping("/upload")
    public Long upload(@RequestBody PhotoRequest photoRequest) {
        return photoService.photoSave(photoRequest);
    }



    @GetMapping("/upload")
    public String yes() {
        return "yes";
    }
}


