package com.example.restfulwebservice.user;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {
    private UserDaoService service;
    //위의 인스턴스에 직접적으로 new를 통해 선언해서 사용하는 것이 아니라
    //아래와 같이 의존성을 주입한다

    public UserController(UserDaoService service){
        this.service = service;
    }
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){

        return service.findAll();
    }

    // GET / users/1 or users/10
    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id){ //string으로 지정하지 않아도 자동으로 컨버팅된다.
        User user = service.findOne(id);
        //findOne에서 오른클릭을 하고 Refacter -> introduce variable 를 통해 변수명을 변경한다.
        if(user == null){
            throw new UserNotFoundExceptuon(String.format("ID[%s] not found", id));
        }// 이 에외 코드 방법은 500번을 호출하지만 보안이 좋지 못하다

        //return user; hateoas 적용전 User -> return user

        //hateoas 적용하기 하나의 리소스에서 추가로 사용할 수 있는 자원을 확인 할수 있다.
        Resource<User> resource = new Resource<>(user);
        //user가 사용할 추가적인 정보 넣기
//        ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
//            ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users")); // 해당 url로 적용한다.
        return resource;
    }



    //vaildaion 기능을 사용하려고 하는 곳은 이부분이다
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){ //지금 전달 받고자 하는 데이터가 requestbody형식임을 알림
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()//현재 요정된 requset값을 사용한다
                .path("/{id}") //반환 요청된 경로 설정
                .buildAndExpand(savedUser.getId()) //save되어진 id값을 사용한다
                .toUri(); //모든 형태는 uri형태로 한다

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null)
            throw new UserNotFoundExceptuon(String.format("ID[%s] not found", id));
    }

    //putmapping으로 수정작업을 할수 있다다
}
