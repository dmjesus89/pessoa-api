package br.com.pamcary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.web.servlet.MockMvc;

import br.com.pamcary.controller.PessoaController;
import br.com.pamcary.dto.PessoaFisicaDTO;
import br.com.pamcary.entity.PessoaFisicaEntity;
import br.com.pamcary.repository.PessoaRepository;
import br.com.pamcary.service.PessoaService;

@TestComponent
public class PessoaControllerTest {

	@InjectMocks
	private PessoaController controller;

	@InjectMocks
	private PessoaService pessoaService = new PessoaService();

	@Mock
	private PessoaRepository pessoaRepository;

	private MockMvc mockMvc;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		//mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void test() throws Exception {
		
		
		PessoaFisicaEntity pessoaPersistida = new PessoaFisicaEntity();
		pessoaPersistida.setNome("pedro");
		pessoaPersistida.setCpf("carlos");
		pessoaPersistida.setDataNascimento(LocalDate.now());

		when(pessoaRepository.save(any(PessoaFisicaEntity.class))).thenReturn(pessoaPersistida);
		
		PessoaFisicaDTO pessoaCriada = new PessoaFisicaDTO();
		pessoaCriada.setNome("Diego");
		pessoaCriada.setCpf("Test1");
		pessoaCriada.setDataNascimento(LocalDate.now());
		
		PessoaFisicaEntity pessoaRetornada = pessoaService.save(pessoaCriada);
		
		  System.out.println(pessoaRetornada.getCodigo());
		  System.out.println(pessoaRetornada.getNome());
		

//		PessoaFisicaEntity newpessoaEntity = new PessoaFisicaEntity();
//		newpessoaEntity.setNome("Diego");
//		newpessoaEntity.setCpf("Test1");
//		newpessoaEntity.setDataNascimento(LocalDate.now());
//
//		when(pessoaRepository.save(any(PessoaFisicaEntity.class))).thenReturn(newpessoaEntity);
//
//		pessoaService.save(newpessoaEntity);
//		mockMvc.perform(post("/admin/users")).andDo(print());

	}

}
