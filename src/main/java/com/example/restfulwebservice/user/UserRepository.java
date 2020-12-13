package com.example.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //data에 관련된 repository라고 지정한다.
public interface UserRepository extends JpaRepository<User, Integer> { //JpaRepository를 상속받고 어떤 클래스를 사용 할 것인지 지정해야한다
                                        //해당 클래스명과 기본키의 타입을 작성한다

}
