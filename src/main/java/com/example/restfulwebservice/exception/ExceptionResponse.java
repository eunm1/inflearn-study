package com.example.restfulwebservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//lombok어노테이션 사용해서 자동으로 매소드를 생성한다
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    //예외상태를 클래스와 리소스에서 사용하기
    private Date timestamp; //예외 발생시간
    private String message; //예외 메세지
    private String details; //예외 상세 정보

    //예외 처리 되는 공통적인 메소드가 필요하다



}
