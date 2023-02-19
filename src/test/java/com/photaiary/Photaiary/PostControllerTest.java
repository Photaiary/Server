package com.photaiary.Photaiary;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.photaiary.Photaiary.post.daily.Daily;
import com.photaiary.Photaiary.post.diary.Diary;
import com.photaiary.Photaiary.post.diary.DiaryPostRequestDto;
import com.photaiary.Photaiary.post.diary.DiaryRepository;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private DiaryRepository postsRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글작성() throws Exception{
        // ※ daily에 대한 테스트 내용 -> 차후, Daily와 User Token 만든 이후 테스트 코드에 추가

        //given
        DiaryPostRequestDto requestDto = DiaryPostRequestDto.builder()
                .diaryTitle("title")
                .diaryContent("content")
                .build();

        Diary savedDiary = postsRepository.save(requestDto.toEntity());

        Long savedId = savedDiary.getDiaryIndex();
        String expectedTitle = "title";
        String expectedContent = "content";

        String url ="http://localhost:"+port+"/daily/1/diary";

        //when
        ResponseEntity<Diary> responseEntity = testRestTemplate.postForEntity(url,requestDto, Diary.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Diary> diaries = postsRepository.findAll();
        assertThat(diaries.get(0).getDiaryIndex()).isEqualTo(savedId);
        assertThat(diaries.get(0).getDiaryTitle()).isEqualTo(expectedTitle);
        assertThat(diaries.get(0).getDiaryContent()).isEqualTo(expectedContent);
    }

    @Test
    public void 게시글수정() throws Exception{
        //given
        Diary savedDiary = postsRepository.save(Diary.builder()
                .diaryTitle("diaryTitle")
                .diaryContent("diaryContent")
                .build());

        Long updateId = savedDiary.getDiaryIndex();
        String expectedTitle = "Changed Title";
        String expectedContent = "Changed Content";

        DiaryPostRequestDto requestDto = DiaryPostRequestDto.builder()
                .diaryTitle(expectedTitle)
                .diaryContent(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/diary/1";

        HttpEntity<DiaryPostRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Diary> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Diary.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Diary> diaries = postsRepository.findAll();
        assertThat(diaries.get(0).getDiaryTitle()).isEqualTo(expectedTitle);
        assertThat(diaries.get(0).getDiaryContent()).isEqualTo(expectedContent);
    }
}