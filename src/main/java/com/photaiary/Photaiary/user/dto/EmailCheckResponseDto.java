package com.photaiary.Photaiary.user.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailCheckResponseDto {
   private Boolean isSuccess;
   private String code;

}
