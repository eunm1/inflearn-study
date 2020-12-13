package com.example.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa") // 모든 메소드가 가지는 프리픽스 값
public class UserJpaController {

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    //http://localhost:8088/jpa/users
    //첫번째 url 메소드 지정
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id); // Optional<T>는 반환값이 있을 수도 있고 없을 수도 있기 때문에
        //반환값을 상속클래스 형식과 같아야 한다.
        if(!user.isPresent()){ //id가 db에 없다면
            throw new UserNotFoundExceptuon(String.format("ID{%s} not found", id));
        }

        //헤테오스 기능 사용한다면 이 기능을 통해 주솟값을 추가할 수있다.
        Resource<User> resource = new Resource<>(user.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        //@vaild : 유효성 검사 관련 어노테이션
        //@RequestBody : 사용자들로 부터 데이터를 json형식을 받기 위한 어노테이션

        User savedUser = userRepository.save(user); //user라는 도메인 객체를 받아 저장한다.


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //현재 생성되어 있는 아이디 값에 데이터를 헤더값으로 전달해 줄것이다
                .buildAndExpand(savedUser.getId()) //저장되어진 객체의 id값을 컬리브레이스에 맵핑해서 전달할 것
                .toUri(); //uri 데이터로 변경한다.

        return ResponseEntity.created(location).build();
    }

    // /jpa/users/90001/posts
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){ //유저 정보를 통해 post 데이터를 가져오겠다
        Optional<User> user = userRepository.findById(id); // Optional<T>는 반환값이 있을 수도 있고 없을 수도 있기 때문에
        //반환값을 상속클래스 형식과 같아야 한다.
        if(!user.isPresent()){ //id가 db에 없다면
            throw new UserNotFoundExceptuon(String.format("ID{%s} not found", id));
        }

        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){
        //Post객체에 @vaild을 설정하지 않아서 vaildation check를 사용할 수 없다
        //@RequestBody : 사용자들로 부터 데이터를 json형식을 받기 위한 어노테이션

        //사용자 정보의 아이디값을 post에 지정을 먼저 하고 저장해야한다
        Optional<User> user = userRepository.findById(id); // Optional<T>는 반환값이 있을 수도 있고 없을 수도 있기 때문에

        if(!user.isPresent()){ //id가 db에 없다면
            throw new UserNotFoundExceptuon(String.format("ID{%s} not found", id));
        }

        post.setUser(user.get());
        Post savedPost = postRepository.save(post); //사용자 정보를 추가한다

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") //현재 생성되어 있는 아이디 값에 데이터를 헤더값으로 전달해 줄것이다
                .buildAndExpand(savedPost.getId()) //저장되어진 객체의 id값을 컬리브레이스에 맵핑해서 전달할 것
                .toUri(); //uri 데이터로 변경한다.

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}/posts")
    public void deletePost(@PathVariable int id){
        postRepository.deleteById(id);
    }
}
