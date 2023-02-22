//package com.photaiary.Photaiary;
//
//
//import com.photaiary.Photaiary.post.daily.Daily;
//import com.photaiary.Photaiary.post.diary.Diary;
//import com.photaiary.Photaiary.post.diary.DiaryPostRequestDto;
//import com.photaiary.Photaiary.post.diary.DiaryRepository;
//import org.junit.jupiter.api.AfterEach;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class PostControllerTest {
//
//    @LocalServerPort
//    private int port;
//    @Autowired
//    private DiaryRepository postsRepository;
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        postsRepository.deleteAll();
//    }
//
//    @Test
//    public void 게시글작성() throws Exception{
//        //given
//        DiaryPostRequestDto requestDto = DiaryPostRequestDto.builder()
//                .diaryTitle("title")
//                .diaryContent("content")
//                .build();
//
////        Diary savedDiary = postsRepository.save(Diary.builder()
////                .diaryTitle("title")
////                .diaryContent("content")
////                .daily(daily)
////                .build());
//        Diary savedDiary = postsRepository.save(requestDto.toEntity());
//
//        Long savedId = savedDiary.getDiaryIndex();
//        String expectedTitle = "title";
//        String expectedContent = "content";
//
//        String url ="http://localhost:"+port+"/daily/1/diary";
//
//        //when
//        ResponseEntity<Diary> responseEntity = testRestTemplate.postForEntity(url,requestDto, Diary.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        List<Diary> diaries = postsRepository.findAll();
//        assertThat(diaries.get(0).getDiaryIndex()).isEqualTo(savedId);
//        assertThat(diaries.get(0).getDiaryTitle()).isEqualTo(expectedTitle);
//        assertThat(diaries.get(0).getDiaryContent()).isEqualTo(expectedContent);
//    }
//}