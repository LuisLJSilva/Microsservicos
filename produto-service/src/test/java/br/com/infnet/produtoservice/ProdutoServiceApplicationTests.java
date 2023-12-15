package br.com.infnet.produtoservice;

import info.schnatterer.mobynamesgenerator.MobyNamesGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProdutoServiceApplicationTests {

	@Test
	void contextLoads() {
		System.out.println(MobyNamesGenerator.getRandomName());
	}

}
