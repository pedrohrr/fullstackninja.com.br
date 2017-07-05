package br.com.fullstackninja.h2dbspringdata.controller;

import br.com.fullstackninja.h2dbspringdata.entity.Pessoa;
import br.com.fullstackninja.h2dbspringdata.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaController {

	@Autowired
	private PessoaRepository repository;

	@GetMapping
	public Iterable<Pessoa> list() {
		return repository.findAll();
	}

}