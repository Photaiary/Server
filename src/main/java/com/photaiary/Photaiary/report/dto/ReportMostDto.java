package com.photaiary.Photaiary.report.dto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ReportMostDto {
    private String most;
    private Long photoCount;
    private Integer month;
    private String comment;
    private String top3;
}
