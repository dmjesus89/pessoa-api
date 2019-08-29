package br.com.pamcary.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pamcary.controller.util.HeaderUtil;
import br.com.pamcary.dto.PessoaFisicaDTO;
import br.com.pamcary.entity.PessoaFisicaEntity;
import br.com.pamcary.exception.BadRequestAlertException;
import br.com.pamcary.exception.CpfEmUsoException;
import br.com.pamcary.exception.PessoaNotFoundException;
import br.com.pamcary.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "pessoas", description = "Tudo sobre pessoas", produces = "application/json")
@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PessoaController {

	@Autowired
	private PessoaService pessoaService;

	private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

	/**
	 * POST /pessoas : criar uma nova pessoa.
	 * <p>
	 * criar uma nova pessoa caso o cpf não exista na base
	 *
	 * @param pessoaDTO a pessoa a ser criada
	 * @return o ResponseEntity com status 201 (Created) and e com o body da nova
	 *         pessoa , or com status 400 (Bad Request) se o cpf ja estiver em uso
	 * @throws URISyntaxException       se o Location URI syntax estiver incorreto
	 * @throws BadRequestAlertException 400 (Bad Request) se o codigo foi informado
	 * @throws CpfEmUsoException        400 (Bad Request) se o cpf já estiver em uso
	 */
	@ApiOperation(value = "Método Post para cadastro de pessoa com validação de duplicidade para o campo cpf", notes = "criar nova pessoa")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 404, message = "CPF já em usi ", response = CpfEmUsoException.class),
			@ApiResponse(code = 201, message = "Recurso criado") })
	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody PessoaFisicaDTO pessoaDTO, BindingResult result)
			throws URISyntaxException {

		logger.debug("REST request para salvar Pessoa : {}", pessoaDTO);

		if (pessoaDTO.getCodigo() != null) {
			throw new BadRequestAlertException("Não informar id para cadastro");
		}

		PessoaFisicaEntity pessoaEntity = pessoaService.save(pessoaDTO);

		BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

		return ResponseEntity.created(new URI("/api/pessoas/" + pessoaDTO.getCodigo()))
				.headers(HeaderUtil.createAlert(
						"uma nova pessoa foi criada com o identificador " + pessoaDTO.getCodigo(),
						pessoaDTO.getCodigo().toString()))
				.body(pessoaDTO);

	}

	/**
	 * PUT /pessoas/id : alterar uma pessoa existente.
	 *
	 * @param pessoaDTO a pessoas a ser alterada
	 * @return the ResponseEntity com status 200 (OK) e com o body da pessoa
	 *         alterada
	 * @throws BadRequestAlertException 400 (Bad Request) se o codigo nao foi
	 *                                  informado
	 * @throws CpfEmUsoException        400 (Bad Request) se o cpf já estiver em uso
	 * @throws PessoaNotFoundException  404 (notfound) pessoa não encontrada
	 */
	@ApiOperation(value = "Método PUT para alterar informações da pessoa com validação de duplicidade para o campo cpf", notes = "alterar pessoa")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Fields are with validation errors"),
			@ApiResponse(code = 404, message = "CPF já usado", response = CpfEmUsoException.class),
			@ApiResponse(code = 404, message = "Not Found", response = PessoaNotFoundException.class),
			@ApiResponse(code = 200, message = "Recurso alterado") })
	@PutMapping
	public ResponseEntity<PessoaFisicaDTO> update(@Valid @RequestBody PessoaFisicaDTO pessoaDTO) {
		logger.debug("REST request para alterar a pessoa : {}", pessoaDTO);

		if (pessoaDTO.getCodigo() == null) {
			throw new BadRequestAlertException("Obrigatório informar o id");
		}

		pessoaService.save(pessoaDTO);

		return ResponseEntity.ok(pessoaDTO);
	}

	/**
	 * GET /pessoa/:id : o código "id" pessoa.
	 *
	 * @param id o código da pessoa para pesquisar
	 * @return the ResponseEntity com status 200 (OK) e com o body do "id" pessoa,
	 *         ou com status 404 (Not Found)
	 * @throws PessoaNotFoundException 404 (notfound) pessoa não encontrada
	 */
	@ApiOperation(value = "Método Get para retornar uma pessoa pelo código", notes = "Retornar pessoa por id")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "código pessoa para retorno", required = true, dataType = "Long", paramType = "path", defaultValue = "1") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso", response = PessoaFisicaEntity.class),
			@ApiResponse(code = 404, message = "Not Found", response = PessoaNotFoundException.class),
			@ApiResponse(code = 500, message = "Falha") })
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id) {

		logger.debug("REST request para retornar uma Pessoa : {}", id);

		PessoaFisicaEntity pessoaEntity = pessoaService.findById(id);

		PessoaFisicaDTO pessoaDTO = new PessoaFisicaDTO();
		BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

		return new ResponseEntity<PessoaFisicaDTO>(pessoaDTO, HttpStatus.OK);
	}

	/**
	 * DELETE /pessoas/:id : deletar com o código da pessoa.
	 *
	 * @param id o código da pessoa para deletar
	 * @return the ResponseEntity com status 200 (OK)
	 * @throws PessoaNotFoundException 404 (notfound) pessoa não encontrada
	 */
	@ApiOperation(value = "Método Delete para deletar uma pessoa pelo código")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "código pessoa para retorno", required = true, dataType = "Long", paramType = "path", defaultValue = "1") })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucesso", response = Long.class),
			@ApiResponse(code = 404, message = "Not Found", response = PessoaNotFoundException.class),
			@ApiResponse(code = 500, message = "Falha") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.debug("REST request para retornar deletar uma Pessoa : {}", id);

		pessoaService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createAlert("Uma pessoa foi deletada com o id", "" + id))
				.build();
	}

}
