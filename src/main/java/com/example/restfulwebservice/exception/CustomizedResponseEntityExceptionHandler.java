package com.example.restfulwebservice.exception;

import com.example.restfulwebservice.user.UserNotFoundExceptuon;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice //모든 controller가 실행이 될때 해당 hadler가 우선적으로 실행된다.
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    //어떤 컨트롤러 클래스가 실행되면 해당 핸들러가 실행이 된다
    //이 클래스 안에서 예외가 발생하면 메소드가 실행이 된다.
    @ExceptionHandler(Exception.class)//exceptionhandler로 지정
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); //500번대 에러로 지정
    } // 모든 exception 처리

    @ExceptionHandler(UserNotFoundExceptuon.class)//exceptionhandler로 지정
    public final ResponseEntity<Object> handleUserNotFoundExceptuon(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND); //404번 에러로 지정
    }

    /*final을 사용하는 이유
    CustomizedResponseEntityExceptionHandler클래스는 Response로 응답시 발생될 수 있는 모든 예외처리를 담당하는 의미로 선언된 클래스입니다.

    그 중 handleAllExcpetion는 Validation 오류와 UserNotFoundException을 제외한 모든 예외처리를 처리하기 위한 예외 처리 메소드로써,
    더이상 하위 클래스를 파생해서 override되지 않게 하기 위해 final로 선언하였습니다.

    물론 final로 선언하지 않고 사용하셔도 되며, 그럴 경우에는 다른 곳에서 CustomizedResponseEntityExceptionHandler 클래스를 상속받은 다음,
     @Override할 수 있게 됩니다. 그러면, 우리가 정의한 내용에 덮어져서 새로운 메소드 처리가 됩니다.

    */

    //valid 에러 발생시 나타낼 값 정의
    //override마크를 해야 해당 위치에 에러가 있을 경우 위치를 알수 있다.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        /*ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ex.getMessage(),
                ex.getBindingResult().toString());*/
        //현재 에러 발생 시간 , 에러 메세지 작성, 메세지 결과값을 string으로 적용
        //위의 값이 복잡하므로 알아보기 쉽게 수정한다.
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validatuon faild",
                ex.getBindingResult().toString());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        //responseEntity의 형태로 반환시킨다, 잘못된 요청값에 해당하는 에러코드 적절하게 적용
    }
}
