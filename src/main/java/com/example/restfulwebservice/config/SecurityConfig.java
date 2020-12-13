package com.example.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration // 해당 어노테이션을 설정하면 springboot가 실행될때 메모리 설정정보가 로드된다
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //security 웹 설정을 하기 위한 클래스 상속받음

    //h2db연결을 위한 함수 추가
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();//h2-console/밑에 오는 html을 모두 허용시킨다는 의미
        http.csrf().disable(); //csff를 사용하지 않겠다
        http.headers().frameOptions().disable();// 사용하지 않겠

    }

    @Autowired //의존성 주읩
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        // 방법 1
        // auth.jdbcAuthentication()
        // 방법 2
        // 메모리 방식
        auth.inMemoryAuthentication()
                .withUser("kem")
                .password("{noop}test1234")
                //{noop}해당 비밀 번호 값을 인코딩 없이 사용할 수 있도록하는 코드
                //실제 서버를 생성할 때는 {noop}를 사용하지 말고 적절한 인코딩 알고리즘 사용한다
                .roles("USER");
    }
}
