package com.photaiary.Photaiary.post.photo.service;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.photo.dto.PhotoRequest;
import com.photaiary.Photaiary.post.photo.dto.PhotoS3Dto;
import com.photaiary.Photaiary.post.photo.entity.DeleteStatus;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import com.photaiary.Photaiary.post.photo.vo.PhotoVo;
import com.photaiary.Photaiary.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoServiceTest {

    @LocalServerPort
    int port;
    @Autowired
    private PhotoService photoService;
    @Mock
    private DailyReposiotry dailyReposiotry;
    @Autowired
    private PhotoRepository photoRepository;
    @BeforeEach
    public void setup(){
        user = User.builder()
                .name("박정우")
                .nickname("망그러진곰")
                .email("mGom@gommm.com")
                .birthdate("970727")
                .password("abcd12!")
                .build();
        daily = Daily.builder()
                .user(user)
                .dailyValue("230208")
                .build();
        photoRequest = PhotoRequest.builder()
                .dailyId(dailyId)
                .tagListString("['안녕', '친구야']")
                .comment(comment)
                .build();
    }
    @AfterEach
    public void cleanup() {
        dailyReposiotry.deleteAll();
    }
    PhotoRequest photoRequest;
    private User user;
    private Daily daily;
    private Photo photo;
    private Long dailyId;
    private final String comment = "abcd";
    private final DeleteStatus deleteStatus = DeleteStatus.not_exist;
    private final String tag = "000000001";
    @Test
    @DisplayName("사진 정보 저장 기능")
    public void test_photoSave() throws Exception {
        PhotoVo photoVo = new PhotoVo();
        assertThat(photoRepository.findById(photoService.photoInfoSave(photoRequest, photoVo, (PhotoS3Dto)null))).isNotNull();
    }
    @Test
    @DisplayName("사진 정보 수정 기능")
    public void test_photoEdit() {

    }
}
