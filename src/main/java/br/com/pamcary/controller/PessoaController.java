package br.com.pamcary.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.pamcary.controller.util.HeaderUtil;
import br.com.pamcary.controller.util.PaginationUtil;
import br.com.pamcary.dto.PessoaFisicaDTO;
import br.com.pamcary.entity.PessoaFisicaEntity;
import br.com.pamcary.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Pessoa")
@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

	/**
	 * POST /pessoas : Creates a new person.
	 * <p>
	 * Creates a new person if the name is not already used
	 *
	 * @param pessoaDTO the person to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         person, or with status 400 (Bad Request) if the name is already in
	 *         use
	 * @throws URISyntaxException       if the Location URI syntax is incorrect
	 * @throws BadRequestAlertException 400 (Bad Request) if the name is already in
	 *                                  use
	 */
	@ApiOperation(value = "Método Post para cadastro de pessoa com validação de duplicidade para o campo nome")
	@PostMapping
	@Timed
	public ResponseEntity<?> save(@Valid @RequestBody PessoaFisicaDTO pessoaDTO, BindingResult result) {

		logger.debug("REST request para salvar Pessoa : {}", pessoaDTO);

		PessoaFisicaEntity pessoaEntity = pessoaService.save(pessoaDTO);

		BeanUtils.copyProperties(pessoaDTO, pessoaEntity);

		return new ResponseEntity<PessoaFisicaDTO>(pessoaDTO, HttpStatus.CREATED);
	}

	/**
	 * GET /pessoa/:id : get the "id" pessoa.
	 *
	 * @param id the id of the pessoas to find
	 * @return the ResponseEntity with status 200 (OK) and with body the "id"
	 *         pessoa, or with status 404 (Not Found)
	 */
	@ApiOperation(value = "Método Get para retornar uma pessoa pelo código")
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {

		logger.debug("REST request para retornar uma Pessoa : {}", id);

		PessoaFisicaEntity pessoaEntity = pessoaService.findById(id);

		PessoaFisicaDTO pessoaDTO = new PessoaFisicaDTO();
		BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

		return new ResponseEntity<PessoaFisicaDTO>(pessoaDTO, HttpStatus.OK);
	}

	/**
	 * GET /pessoas : get all pessoas.
	 *
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all users
	 */
	@ApiOperation(value = "Método Get para retornar todas as pessoas")
	@GetMapping
	public ResponseEntity<List<PessoaFisicaEntity>> getAll(Pageable pageable) {
		logger.debug("REST request para retornar todas Pessoa : {}");

		Page<PessoaFisicaEntity> page = pessoaService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pessoas");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * DELETE /pessoas/:id : delete the "id" Pessoa.
	 *
	 * @param id the id of the pessoa to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@ApiOperation(value = "Método Delete para deletar uma pessoa pelo código")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.debug("REST request para retornar deletar uma Pessoa : {}", id);

		pessoaService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createAlert("Uma pessoa foi deletada com o id", "" + id)).build();
	}

//	@GetMapping
//	public PagedResponse<PollResponse> getPolls(
//			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
//		return pollService.getAllPolls(currentUser, page, size);
//	}

}
