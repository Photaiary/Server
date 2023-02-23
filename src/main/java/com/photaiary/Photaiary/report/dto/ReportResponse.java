package com.photaiary.Photaiary.report.dto;
import lombok.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
@Getter
@Setter
public class ReportResponse {
    private List<Map<String, ReportMostDto>> result;
    public ReportResponse() {
        result = new ArrayList<>();
    }
}
