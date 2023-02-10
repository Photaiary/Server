package com.photaiary.Photaiary.post.photo.controller;

import com.photaiary.Photaiary.post.photo.dto.EditRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RequiredArgsConstructor
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;
    
    @PostMapping("/upload")
    public Long upload(@RequestBody PhotoRequest photoRequest) {
        return photoService.photoSave(photoRequest);
    }

    @PutMapping("/edit")
    public Long update(@RequestBody EditRequest editRequest) {
        return photoService.photoEdit(editRequest);
    }

    @PostMapping("/uploadtest")
    public String uploadTest(@RequestParam("img") MultipartFile img) {
        try {
            javaxt.io.Image image = new javaxt.io.Image();
            System.out.println(img.getOriginalFilename());
            return img.getOriginalFilename();
        } catch(Exception e){
            System.out.println("no");
            return "wrong";
        }
    }

    @GetMapping("/upload")
    public String yes() {
        return "yes";
    }
}


