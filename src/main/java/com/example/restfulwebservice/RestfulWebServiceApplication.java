package com.example.restfulwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulWebServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(RestfulWebServiceApplication.class, args);
	}

	//다국어 변환할수 있게 하기 다국어 처리를 위한 internationalization 구현
    //SpringBootApplication이라는 어노테이션을 가진 클래스에 bean을 작성하면
    //스프링 부트가 초기화 될때 해당 메서드에 해당하는 값이 메모리에 올라가서 다국적인 클래스를 사용할 수 있다.
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }
    //다국어를 사용하기 위해서는 다국적파일을 사용해야 하는데
    // 그것은 application.yml에서 작성한다. 임시로 messages라는 이름을 가진다
    //resources에 해당이름의 properties를 작성하고 각각의 국가별 메세지 파일을 작성한다.


}
