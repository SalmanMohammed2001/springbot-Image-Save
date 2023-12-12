package com.image.save.com.image.save;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public Gson getGson(){
		return new Gson();
	}

	@Bean
	public ModelMapper getModelMapper(){
		return new ModelMapper();
	}

}
