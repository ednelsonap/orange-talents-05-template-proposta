package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuscarPropostaController {

	@Autowired
	private PropostaRepository propostaRepository;
	
	@GetMapping("/api/propostas/{id}")
	private ResponseEntity<Object> buscarProposta(@PathVariable(required = true, value = "id") String idProposta) {
		Optional<Proposta> possivelProposta = propostaRepository.findByUuid(idProposta);
		if (possivelProposta.isEmpty()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(new PropostaResponse(possivelProposta.get()));
	}
}
