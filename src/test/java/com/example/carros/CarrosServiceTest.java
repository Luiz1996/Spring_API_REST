package com.example.carros;

import com.example.carros.api.infra.Exception.ObjectNotFoundException;
import com.example.carros.api.carros.Carro;
import com.example.carros.api.carros.CarroService;
import com.example.carros.api.carros.CarroDTO;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarrosServiceTest {

	@Autowired
	private CarroService carroService;

	@Test
	void initialTest() {

		//inserindo carro
		Carro carro = new Carro();
		carro.setTipo("Tipo do Carro");
		carro.setNome("Nome do Carro");

		CarroDTO c = carroService.insert(carro);

		//validando se inseriu mesmo
		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		//buscando objeto na base
		c = carroService.getCarroById(id);
		Assert.notNull(c);

		//validando campos obtidos na consulta
		assertEquals("Nome do Carro", c.getNome());
		assertEquals("Tipo do Carro", c.getTipo());

		//deletar carro
		carroService.delete(id);

		//validando se inseriu mesmo
		try{
			Assert.isNull(carroService.getCarroById(id));
			fail("O carro não foi excluído");
		}catch (ObjectNotFoundException ob){
			//OK, deu certo!
		}
	}

	@Test
	void listTest(){
		List<CarroDTO> c = carroService.getCarros(PageRequest.of(0,30));

		assertEquals(60, c.size());
	}
}
