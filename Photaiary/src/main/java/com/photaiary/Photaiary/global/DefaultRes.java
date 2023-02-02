package com.photaiary.Photaiary.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class DefaultRes<T> {

    private int statusCode;  // 상태 코드
    private String responseMessage; // 부가 설명
    private T data; // 응답 데이터


    // 요청 성공의 경우
    public static<T> DefaultRes<T> res(T data){
        return DefaultRes.<T>builder()
                .statusCode(HttpStatus.OK.value())
                .responseMessage("요청에 성공하였습니다.")
                .data(data)
                .build();
    }

    // 요청 실패한 경우
//    public static<T> DefaultRes<T> res(StatusCode statusCode) {
//        return DefaultRes.<T>builder()
//                .statusCode(statusCode.getStatusCode())
//                .responseMessage(statusCode.getResponseMessage())
//                .build();
//    }

//    public static<T> DefaultRes<T> res(StatusCode statusCode, final T t) {
//        return DefaultRes.<T>builder()
//                .statusCode(statusCode.getStatusCode())
//                .responseMessage(statusCode.getResponseMessage())
//                .data(t)
//                .build();
//    }
}
