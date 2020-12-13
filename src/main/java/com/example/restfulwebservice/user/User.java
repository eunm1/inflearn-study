package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
//@JsonIgnoreProperties(value={"ssn"}) //방법 3-2(클래스 블록단위 어노테이션)
@NoArgsConstructor //UserV2에 상속할 때 연결할 디폴드 생성자를 지정해야한다. 어노테이션으로 지정
//@JsonFilter("UserInfo") //adminUserController사용 //hateoas를 사용하기 위해 주석처리
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체") // swagger 처리를 위한
@Entity //jpa를 위한 설정
public class User {

    @Id //jpa를 위한 설정
    @GeneratedValue //jpa를 위한 설정
    private Integer id;

    //유효성 검사를 통한 vailidation api사용하기
    //글가 크기가 해당 크기 이상 사용할 수 있다는 어노테이션
    //사이즈 지정과 동시에 메세지 전달하기 : 에러메세지의 세부사항에 추가되어 나타난다.
    @Size(min = 2, message = "Name은 두글자 이상이어야 합니다.")
    @ApiModelProperty(notes = "사용자 이름을 입력해 주세요") // swagger에서 사용할 위한
    private String name;

    //현재 회원이 가입하는 날짜는 미래가 아닌 과거만 쓸수 있다는 어노테이션
    @Past
    @ApiModelProperty(notes = "사용자 등록일 을 입력해 주세요")
    private Date joinDate;
    //allargsconstructor를 통해? getter setter 가 자동으로 생성된다.


    //response 데이터 제어를 위한 filtering
    //외부에 노출시키도 싶지 않은 정보를 필터링한다
    //방법 1 : **모양으로 대체, 방법 2 : null값으로 대체
    // 방법 3 : spring boot에서 filtering을 위한 특정한 어노테이션을 추가한다.
    //방법 4 : 프로그래밍으로 제어하는 filtering 방법
    //방법 3-1 (변수 어노테이션)
//    @JsonIgnore
    @ApiModelProperty(notes = "사용자 비밀번호을 입력해 주세요")
    private String password;
    @ApiModelProperty(notes = "사용자 주민번호을 입력해 주세요")
    private String ssn;


    //jpa post 적용 변수 설정
    @OneToMany(mappedBy = "user") //한명의 사용자가 여러 게시물 생성가능
    private List<Post> posts;

    //post데이터를 가지고 있지 않은 생성자를 만들어 준다
    //새로운 변수를 추가하면서 자동으로 추가된 생성자에 변수가 적용되었고
    //Dao에서 사용하는 생성자와 차이가 생김
    public User(int id, String name, Date joinDate, String password, String ssn) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
    }
}
