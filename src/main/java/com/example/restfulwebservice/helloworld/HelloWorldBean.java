package com.example.restfulwebservice.helloworld;

//Lombok이라는 디펜던시를 추가 했었는데 이는 자동 생성기능이 있어서 개발시간을 줄일수 있다

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldBean {
    private String message;

    //lombok을 사용하기 때문에 getter, setter를 생성하지 않아도 된다.
    //대신 특별한 annotation을 사용한다. 그럼 아래의 코드를 사용하지 않아도 된다.
/*    public HelloWorldBean(String message){

    }*/
    //lombok plugin을 설치하면 allargsConstructor를 통해 생성하지 않아도 되는 메소드에 빨간줄 표시가 된다.
    //@NoArgsConstructor는 선언하지 않아도 자동으로 디볼프 생성자가 적용된다.
    //setting -> annotation검색후 annotationprocessor를 enable한다.

    //자동으로 생성되는 메소드, 생성자는 intellij 왼쪽 메뉴에 structure를 통해 확인할 수 있다

}
