package com.back.global;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration // @Configuration 어노테이션으로 설정 클래스 지정
public class BaseInitData {

	@Bean
	public ApplicationRunner baseInitDataRunner() { // ApplicationRunner Bean을 통해 애플리케이션 시작 시 초기화 로직 실행
		return args -> {
			log.debug("ApplicationRunner 빈은 스프링에 등록되면 자동으로 실행됩니다");
		};
	}
}
