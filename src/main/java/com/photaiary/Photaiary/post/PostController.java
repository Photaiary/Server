package com.photaiary.Photaiary.post;

import com.photaiary.Photaiary.post.diary.Diary;
import com.photaiary.Photaiary.post.diary.DiaryPostRequestDto;
import com.photaiary.Photaiary.post.diary.DiaryService;
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

    @PostMapping("/daily/{dailyIndex}/diary")
    public Map<Integer, Object> postDiary(@PathVariable Long dailyIndex, @RequestBody DiaryPostRequestDto requestDto) {

        Diary postedDiary = diaryService.save(requestDto);

        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        if (postedDiary.getDiaryIndex() == null) {
            status = HttpStatus.BAD_REQUEST.value();
            data.put("success", "false");
            response.put(status, data);
        } else if (postedDiary.getDiaryIndex() != null) {
            status = HttpStatus.OK.value();
            data.put("success", "true");
            response.put(status, data);
        }
        return response;
    }

    @GetMapping("/daily/{dailyIndex}/diary")
    public List<Diary> findAll(@PathVariable Long dailyIndex) {
        return diaryService.findAll();
    }

}
