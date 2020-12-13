package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor //UserV2에 상속할 때 연결할 디폴드 생성자를 지정해야한다. 어노테이션으로 지정
@JsonFilter("UserInfoV2") //adminUserController사용
public class UserV2 extends User{
    private String grade;
}
