package com.photaiary.Photaiary.post;

import com.photaiary.Photaiary.post.diary.Diary;
import com.photaiary.Photaiary.post.diary.dto.DiaryPostRequestDto;
import com.photaiary.Photaiary.post.diary.dto.DiarySecretDto;
import com.photaiary.Photaiary.post.diary.dto.DiaryUpdateRequestDto;
import com.photaiary.Photaiary.post.diary.service.DiaryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags ="2. 게시글 관련 API")
public class PostController {

    private final DiaryService diaryService;
    private int status;

    @PostMapping("/daily/diary") //index 삭제
    public ResponseEntity<Map<Integer, Object>> postDiary(@RequestBody DiaryPostRequestDto requestDto) throws Exception{
        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        diaryService.save(requestDto);

        status = HttpStatus.OK.value();
        data.put("success", "true");
        response.put(status, data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/daily/{dailyIndex}")
    public ResponseEntity<Map<Integer, Object>> updateDiary(@PathVariable Long dailyIndex, @RequestBody DiaryUpdateRequestDto requestDto) throws Exception{
        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        diaryService.update(dailyIndex, requestDto);

        status = HttpStatus.OK.value();
        data.put("success", "true");
        response.put(status, data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/diary/lock/{dailyIndex}")
    public boolean isPublic(@PathVariable Long dailyIndex) throws Exception{
        boolean isPublic = diaryService.isPublic(dailyIndex);

        return isPublic;
    }
}
