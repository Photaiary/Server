package com.photaiary.Photaiary.report.controller;

import com.photaiary.Photaiary.report.dto.ReportResponse;
import com.photaiary.Photaiary.report.service.ReportService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "6. 리포트 관련 API")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/report/represent")
    public ResponseEntity postDiary() throws Exception {
        ReportResponse reportResponse = reportService.getReport();
        return new ResponseEntity(reportResponse, HttpStatus.OK);
    }
}
