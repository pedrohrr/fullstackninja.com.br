package br.com.fullstackninja.h2dbspringdata.repository;

import br.com.fullstackninja.h2dbspringdata.entity.Pessoa;
import org.springframework.data.repository.CrudRepository;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
}