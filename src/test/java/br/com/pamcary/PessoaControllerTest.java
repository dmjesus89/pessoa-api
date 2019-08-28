package br.com.pamcary;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.pamcary.controller.PessoaController;
import br.com.pamcary.dto.PessoaFisicaDTO;
import br.com.pamcary.entity.PessoaFisicaEntity;
import br.com.pamcary.exception.PessoaNotFoundException;
import br.com.pamcary.repository.PessoaRepository;
import br.com.pamcary.service.PessoaService;

@TestComponent
public class PessoaControllerTest extends AvaliacaoApplicationTests {

//	@InjectMocks
//	private PessoaController controller;
//
//	@InjectMocks
//	private PessoaService pessoaService = new PessoaService();
//
	@Mock
	private PessoaRepository repository;

	@InjectMocks
	private PessoaController controller;

	@Mock
	private PessoaService service;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void testGetById() throws Exception {
		PessoaFisicaEntity pessoaFisicaEntity = new PessoaFisicaEntity();
		pessoaFisicaEntity.setCodigo(1L);
		pessoaFisicaEntity.setNome("Diego Novo");

		when(service.findById(1L)).thenReturn(pessoaFisicaEntity);

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/pessoas/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		Assert.assertEquals(pessoaFisicaEntity.getNome(), "Diego Novo");
	}

	@Test
	public void testSave() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("api/pessoas/").param("nome", "CARLOS")
				.param("cpf", "04064842529").param("dataNascimento", "2019-04-05T10:20"));
	}

	@Test
	public void testSaveAndDelete() throws Exception {
//		PessoaFisicaEntity pessoa = new PessoaFisicaEntity();
//		pessoa.setCodigo(1L);
//		pessoa.setNome("Diego");
//		pessoa.setCpf("");
//		pessoa.setDataNascimento(LocalDateTime.now());
//		when(repository.save(any(PessoaFisicaEntity.class))).thenReturn(pessoa);

		PessoaFisicaEntity pessoa = new PessoaFisicaEntity();
		pessoa.setCodigo(1L);
		pessoa.setNome("Diego");
		pessoa.setCpf("");
		pessoa.setDataNascimento(LocalDateTime.now());
		when(service.save(any(PessoaFisicaDTO.class))).thenReturn(pessoa);

		PessoaFisicaEntity pessoaReturned = service.save(new PessoaFisicaDTO());
		System.out.println(pessoaReturned.getCodigo());
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/pessoas/" + pessoaReturned.getCodigo()));
	}

	@Test
	public void testDeleteFail() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/pessoas/1"));
	}

}
