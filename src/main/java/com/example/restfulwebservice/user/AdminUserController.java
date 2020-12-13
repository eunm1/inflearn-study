package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin") //모든 어노테이션에 공통적으로 붙은 값은 클래스 블록에 설정하는 것이 좋다.
public class AdminUserController {
    private UserDaoService service;
    //위의 인스턴스에 직접적으로 new를 통해 선언해서 사용하는 것이 아니라
    //아래와 같이 의존성을 주입한다

    public AdminUserController(UserDaoService service){
        this.service = service;
    }


    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){

        //return service.findAll() 를
        // ctrl + alt + v or 마우스 오른클릭 메뉴 -> Refactor -> introduce variable을 통해
        // return과 값을 나눠줄 수 있다.
        List<User> all = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","ssn");

        //상위의 filter를 우리가 사용할수 잇는 형채로 변경하기 위한 하단의 코드 (User class의 jsonfilter 아이디, filter)
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        //return 값이 달라지므로 하단의 코드를 적용한다.
        MappingJacksonValue mapping = new MappingJacksonValue(all);
        mapping.setFilters(filters);

        return mapping;
    }

    /* 버전 관리 설명
    * 1, 2는 일반 브라우저에서 실행 가능하다 ex) 1 : Twitter, 2 : Amazon
    * 3, 4는 일반 브라우저에서 실행 불가능 하다 ex) 3 : GitHub, 4 : Microsoft
    * 때문에 별도의 프로그램을 이용해야 한다 ex)postman
    *
    * 버전 관리 주의점
    * uri를 지저분하게 과도하게 정보 표기 사용 x
    * 잘못된 헤더값 사용 주의
    * cach를 적절하게 삭제해줘야 한다
    * 용도에 따라서 일반 브라우저에서 사용할 수 있어야 한다
    * 생성한 api에 대해서 정확한 정보를 사용자에게 제공해 주어야 한다 (API Documentation)
    * */

    // 1. uri를 이용한 rest api version 관리
    // @GetMapping("/v1/users/{id}")
    // 2. requset parameter를 이용한 버전 관리 :
    // postman에서 http://localhost:8088/admin/users/1/?version=1로 검색할 수 있다.
    // @GetMapping(value = "/users/{id}", params = "version=1")
    // 3. hearder를 이용한 api version관리
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    // 4. mim(multiporpose internet mail extenstion) time을 이용한 버전 관리
    // 이메일 형식을 사용해서 이메일 서버에 전달한다. 요즘에는 파일형식으로 사용된다.
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        //클라이언트가 반환하는 값을 filter에 따라 반환될수 있도록 mappingjackson값으로 변경한다.

        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundExceptuon(String.format("ID[%s] not found", id));
        }

        //포함시키고자 하는 filter값 설정
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","password","ssn");

        //상위의 filter를 우리가 사용할수 잇는 형채로 변경하기 위한 하단의 코드 (User class의 jsonfilter 아이디, filter)
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        //return 값이 달라지므로 하단의 코드를 적용한다.
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;
    }

    // 1. uri를 이용한 rest api version 관리
    // @GetMapping("/v2/users/{id}")
    // 2. requset parameter를 이용한 버전 관리 :
    // postman에서 http://localhost:8088/admin/users/1/?version=1로 검색할 수 있다.
    // @GetMapping(value = "/users/{id}", params = "version=2")
    // 3. hearder를 이용한 api version관리
    // postman에서 header의 key값에 X-API-VERSION을 적고 value에 해당 버전 숫자를 적어서 send를 클릭한다.
    // @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    // 4. mim(multiporpose internet mail extenstion) time을 이용한 버전 관리
    // 이메일 형식을 사용해서 이메일 서버에 전달한다. 요즘에는 파일형식으로 사용된다.
    // 이 방식 또한 postman에서 hear값에 지정한다.
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        //클라이언트가 반환하는 값을 filter에 따라 반환될수 있도록 mappingjackson값으로 변경한다.
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundExceptuon(String.format("ID[%s] not found", id));
        }

        //User의 내용을 -> User2 에 복사
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        //포함시키고자 하는 filter값 설정
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        //상위의 filter를 우리가 사용할수 잇는 형채로 변경하기 위한 하단의 코드 (User class의 jsonfilter 아이디, filter)
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        //return 값이 달라지므로 하단의 코드를 적용한다.
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);
        return mapping;
    }
}
