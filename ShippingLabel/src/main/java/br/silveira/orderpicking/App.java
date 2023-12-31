package br.silveira.orderpicking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableAutoConfiguration
@EnableFeignClients(basePackages="br.silveira.orderpicking")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
