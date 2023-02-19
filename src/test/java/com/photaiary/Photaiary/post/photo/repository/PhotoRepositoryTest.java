package com.photaiary.Photaiary.post.photo.repository;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.photo.entity.DeleteStatus;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhotoRepositoryTest {
    @LocalServerPort
    int port;
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
        photo = Photo.builder()
                .daily(daily)
                .latitude(latitude)
                .longitude(longitude)
                .image(imageLink)
                .comment(comment)
                .deleteStatus(deleteStatus)
                .tag(tag)
                .build();
    }
    @AfterEach
    public void cleanup() {
        photoRepository.deleteAll();
    }
    User user;
    Daily daily;
    Photo photo;
    final String latitude = "51.1";
    final String longitude = "52.2";
    final String imageLink = "abcd/abcd";
    final String comment = "abcd";
    final DeleteStatus deleteStatus = DeleteStatus.not_exist;
    final String tag = "000000001";
    @Test
    @DisplayName("사진 정보 저장")
    public void test_save() {
        Long photoId = photoRepository.save(photo).getId();
        assertThat(photoId).isEqualTo(photo.getId());
    }
    @Test
    @DisplayName("유저가 없는 상황에 사진 저장시 오류 검출")
    public void test_save_user() {
        Daily noUserDaily = Daily
                .builder()
                .dailyValue("970727")
                .build();
        Photo noUserPhoto = Photo
                .builder()
                .daily(noUserDaily)
                .build();
        assertThatThrownBy(() -> { photoRepository.save(noUserPhoto); }).isInstanceOf(Exception.class);
//                .hasMessageContaining("boom");
    }

    @Test
    @DisplayName("daily가 없는 상황에 사진 저장시 오류 검출")
    public void test_save_photo() {
        Photo noDailyPhoto = Photo
                .builder()
                .build();
        assertThatThrownBy(() -> { photoRepository.save(noDailyPhoto); }).isInstanceOf(Exception.class);
//                .hasMessageContaining("boom");
    }
    @Test
    @DisplayName("사진 정보 검색")
    public void test_findById() {
        photoRepository.save(photo);
        Photo findPhoto = photoRepository.findById(1L).orElse(null);
        assertThat(findPhoto).isNotNull();
        assertThat(findPhoto.getId()).isEqualTo(1L);
    }
}
