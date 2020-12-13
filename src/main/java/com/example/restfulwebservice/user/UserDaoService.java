package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    //비즈니스 로직을 추가하는 곳
    //사용자 전체 목록 추가
    //상세 목록 보기 등등

    //해당 서비스, component가 어떤 용도로 사용될 것인지 anotation을
    //추가해야 한다.

    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static{
        users.add(new User(1, "kem", new Date(), "pass1","701010-1111111"));
        users.add(new User(2, "ch", new Date(), "pass2","801010-1111111"));
        users.add(new User(3, "net", new Date(), "pass3","901010-1111111"));
    }

    public List<User> findAll(){
        //전체 사용자 목록
        return users;
    }

    public User save(User user){
        //사용자 추가 메소드
        if(user.getId() == null){
            user.setId(++usersCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        //개별 사용자 정보 보기
        for(User user : users){
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    public User deleteById(int id){
        Iterator<User> iterator = users.iterator(); //열거형 타입의 데이터를 Iterator

        while (iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
