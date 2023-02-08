package com.photaiary.Photaiary.post;


import com.photaiary.Photaiary.post.diary.Diary;
import com.photaiary.Photaiary.post.diary.DiaryPostRequestDto;
import com.photaiary.Photaiary.post.diary.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final DiaryService diaryService;
    private int status;

    //Response Body


    @PostMapping("/daily/{dailyIndex}/diary")
    public Map<Integer, Object> postDiary(@PathVariable Long dailyIndex, @RequestBody DiaryPostRequestDto requestDto) {
        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Diary postedDiary = diaryService.save(requestDto);

        if(postedDiary.getDiaryIndex() == null){
            status = HttpStatus.BAD_REQUEST.value();
            data.put("success", "false");
            response.put(status, data);
        } else if (postedDiary.getDiaryIndex()!=null) {
            status = HttpStatus.OK.value();
            data.put("success", "true");
            response.put(status, data);
        }

        return response;
    }

    //추가
    @PutMapping("/diary/{dailyIndex}")
    public Map<Integer,Object> update(@PathVariable Long dailyIndex, @RequestBody DiaryPostRequestDto requestDto){
        Diary updatedDiary = diaryService.updateByIdAndDiaryIndex(dailyIndex, requestDto);

        Map<Integer, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

       if(updatedDiary == null){
            //diaryTitle이나 diaryContent값이 null이면 변경 x
           status = HttpStatus.BAD_REQUEST.value();
           data.put("isSuccess", "false");
           response.put(status, data);
        }else if(updatedDiary != null){
           status = HttpStatus.OK.value();
           data.put("isSuccess", "true");
           response.put(status, data);
       }
        return response;
    }

    //postman 테스트용 Diary 전체조회
    @GetMapping("diary/test")
    public List<Diary> findAll(){
        return diaryService.findAll();
    }
}
