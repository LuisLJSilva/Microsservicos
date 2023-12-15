package br.com.infnet.produtoservice;

import info.schnatterer.mobynamesgenerator.MobyNamesGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;
import java.util.Random;

@SpringBootApplication
public class ProdutoServiceApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ProdutoServiceApplication.class);
		Properties properties = new Properties();
		String appNickName = MobyNamesGenerator.getRandomName();
		properties.put("spring.application.serverNick", appNickName);
		application.setDefaultProperties(properties);
		application.run(args);
	}

}
