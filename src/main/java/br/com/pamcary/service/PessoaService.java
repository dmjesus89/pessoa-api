package br.com.pamcary.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pamcary.dto.PessoaFisicaDTO;
import br.com.pamcary.entity.PessoaFisicaEntity;
import br.com.pamcary.exception.NameAlreadyUsedException;
import br.com.pamcary.exception.PessoaNotFoundException;
import br.com.pamcary.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	private final Logger log = LoggerFactory.getLogger(PessoaService.class);

	public PessoaFisicaEntity save(PessoaFisicaDTO pessoaDTO) throws NameAlreadyUsedException {

		Optional<PessoaFisicaEntity> isPessoaExiste = pessoaRepository.findByNome(pessoaDTO.getNome());
		if (isPessoaExiste.isPresent() && (!isPessoaExiste.get().getCodigo().equals(pessoaDTO.getCodigo()))) {
			throw new NameAlreadyUsedException("Pessoa NAME: '" + pessoaDTO.getNome() + "' já existe");
		}

		PessoaFisicaEntity pessoaEntity = new PessoaFisicaEntity();
		BeanUtils.copyProperties(pessoaEntity, pessoaDTO);

		pessoaRepository.save(pessoaEntity);

		log.debug("Informação criada para pessoa: {}", pessoaEntity);

		return pessoaEntity;

	}

	public PessoaFisicaEntity findById(Long id) {

		Optional<PessoaFisicaEntity> pessoa = pessoaRepository.findById(id);
		
		if(!pessoa.isPresent()) {
			throw new PessoaNotFoundException("Pessoa ID: '" + id + "' Não encontrado");
		}

		log.debug("Informação encontrada para pessoa: {}", id);

		return pessoa.get();
	}

	public Page<PessoaFisicaEntity> findAll(Pageable pageable) {
		return pessoaRepository.findAll(pageable);
	}

	public void delete(Long id) {
		PessoaFisicaEntity pessoaEntity = findById(id);
		pessoaRepository.delete(pessoaEntity);
		log.debug("Informação deletada para pessoa: {}", id);
	}
}
