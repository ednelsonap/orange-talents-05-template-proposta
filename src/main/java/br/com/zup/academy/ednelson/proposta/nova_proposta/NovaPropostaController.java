package br.com.zup.academy.ednelson.proposta.nova_proposta;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class NovaPropostaController {
	
	@Autowired
	private EntityManager manager;

	@PostMapping("/proposta")
	@Transactional
	public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request,
			UriComponentsBuilder uriBuilder){
		
		Proposta proposta = request.toModel();
		manager.persist(proposta);

		URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getUuid()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
