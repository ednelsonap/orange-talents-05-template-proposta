package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.net.URI;
import java.util.Optional;

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
	private PropostaRepository propostaRepository;
	
	@PostMapping("/proposta")
	@Transactional
	public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request,
			UriComponentsBuilder uriBuilder){
		
		Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(request.getDocumento());
		
		if (possivelProposta.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("Já existe uma proposta para este solicitante");
		}
		
		Proposta proposta = request.toModel();
		propostaRepository.save(proposta);

		URI uri = uriBuilder.path("/proposta/{id}").buildAndExpand(proposta.getUuid()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
