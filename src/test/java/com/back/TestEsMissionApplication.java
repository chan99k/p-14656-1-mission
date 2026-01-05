package com.back;

import org.springframework.boot.SpringApplication;

public class TestEsMissionApplication {

	public static void main(String[] args) {
		SpringApplication.from(EsMissionApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
