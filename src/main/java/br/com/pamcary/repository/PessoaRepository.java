package br.com.pamcary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pamcary.entity.PessoaFisicaEntity;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaFisicaEntity, Long> {

	Optional<PessoaFisicaEntity> findByCpf(String cpf);

}
