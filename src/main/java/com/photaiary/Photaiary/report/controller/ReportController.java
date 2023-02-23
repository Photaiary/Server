package com.photaiary.Photaiary.report;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags ="6. 리포트 관련 API")
public class ReportController {

    @GetMapping("/report/represent")
    public Map<Integer, Object> postDiary(@RequestBody ) {

        return response;
    }
}
