package br.com.pamcary.service;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pamcary.dto.PessoaFisicaDTO;
import br.com.pamcary.entity.PessoaFisicaEntity;
import br.com.pamcary.exception.CpfEmUsoException;
import br.com.pamcary.exception.PessoaNotFoundException;
import br.com.pamcary.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	private final Logger log = LoggerFactory.getLogger(PessoaService.class);

	public PessoaFisicaEntity save(PessoaFisicaDTO pessoaDTO) throws CpfEmUsoException {

		Optional<PessoaFisicaEntity> isPessoaExiste = pessoaRepository.findByCpf(pessoaDTO.getCpf());
		if (isPessoaExiste.isPresent() && (!isPessoaExiste.get().getCodigo().equals(pessoaDTO.getCodigo()))) {
			throw new CpfEmUsoException("Pessoa cpf: '" + pessoaDTO.getCpf() + "' já existe");
		}

		if (pessoaDTO.getCodigo() != null) {
			PessoaFisicaEntity pessoaSalva = findById(pessoaDTO.getCodigo());
			BeanUtils.copyProperties(pessoaDTO, pessoaSalva, "codigo");
			pessoaRepository.save(pessoaSalva);
			log.debug("Informação alterada para pessoa: {}", pessoaSalva);
			return pessoaSalva;
		} else {
			PessoaFisicaEntity pessoaEntity = new PessoaFisicaEntity();
			BeanUtils.copyProperties(pessoaDTO, pessoaEntity, "codigo");
			pessoaRepository.save(pessoaEntity);
			log.debug("Informação criada para pessoa: {}", pessoaEntity);
			return pessoaEntity;
		}

	}

	public PessoaFisicaEntity findById(Long id) {

		Optional<PessoaFisicaEntity> pessoa = pessoaRepository.findById(id);

		if (pessoa == null || !pessoa.isPresent()) {
			throw new PessoaNotFoundException("Pessoa código: '" + id + "' Não encontrado");
		}

		log.debug("Informação encontrada para pessoa: {}", id);

		return pessoa.get();
	}

	public void delete(Long id) {
		PessoaFisicaEntity pessoaEntity = findById(id);
		pessoaRepository.delete(pessoaEntity);
		log.debug("Informação deletada para pessoa: {}", id);
	}

}
