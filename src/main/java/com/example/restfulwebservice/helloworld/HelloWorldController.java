package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    //어노테이션을 통한 의존성을 주입시킨다. 다른 방법으로 생성자 와 stter 메서드가 있다.
    @Autowired
    private MessageSource messageSource; //다국어를 처리하기 위한 인스턴스 생성

    //GET
    // /hello-world -> (endpoint)
    // @RequestMapping(method = RequestMethod.Get), path = "") = @GetMapping(path = "")
    @GetMapping(path="/hello-world")
    public String helloWorld(){
        //http://localhost:8088/hello-world
        return "Hello World";
    }

    //단축 아이콘 : alt + enter (마우스 오른키 + generate메뉴 클릭)를 통해서 zmffotm todtjd
    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        //http://localhost:8088/hello-world-bean
        return new HelloWorldBean("Hello World");
    }
    //위와 같은 방법(spring 형태가 아닌 javabean 형태로 반환시키면 spring framwork는 json형태로 변환해서 반환해 주게 된다)

    //json이 아니라 xml형식으로 반환하여면 xml 라이브러리 형식을 적용해야한다다


    @GetMapping(path="/hello-world-bean/path-variable/{name}") //가변변수 활용 url에 넣는 값을 바로 적용한다
    public HelloWorldBean helloWorldBean(@PathVariable String name){
//        return new HelloWorldBean("Hello World "+name); -> {"message":"Hello World kim"}
        return new HelloWorldBean(String.format("Hello World, %s", name)); // -> {"message":"Hello World, kim"}
    }


    //다국어 처리하기 간단 테스트
    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required = false) Locale locale){
        //현재 위치값을 받아오는 locale설정과 해당 값은 요청을 통해 받는다.
        return messageSource.getMessage("greeting.message",null,locale);//(키값, 키값의 파라미터, 위치값)
        //resource에 저장된 국적 언어 대표(?) 값을 가져온다.
    }
}
