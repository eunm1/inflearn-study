package com.example.restfulwebservice.user;

//HTTP Status code
//2xx -> OK
//4xx -> Client 가 잘못함
//5xx -> Server에서 사용하는 리소스와 연결이 안되었거나 프로그램상의 문제일 경우가 많다

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//우리는 5xx에서 예외가 호출되지 않도록 해야한다(어노테이션 추가)
@ResponseStatus(HttpStatus.NOT_FOUND) //404
public class UserNotFoundExceptuon extends RuntimeException {
    public UserNotFoundExceptuon(String message) {
        super(message);
    }
}
