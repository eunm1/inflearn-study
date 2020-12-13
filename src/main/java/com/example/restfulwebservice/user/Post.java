package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id; //기본키 속성

    private String description; //게시물의 내용

    // User : Post -> 1 : (0 ~ N), main : sub -> Parent : Child
    @ManyToOne(fetch = FetchType.LAZY) // 유저 하나에 포스터가 여러개일수 있다.
    @JsonIgnore //json으로 데이터가 표시 되지 않음
    private User user; //어떤 사용자가 작성한 건지 유저 정보 //외부 키 참조
    //FetchType.LAZY : 지연 로딩 방식, 포스트 데이터가 로딩되는 시점에 user데이터를 가져오겠다(매번 post로 받아 오는 것이 아님)
}
