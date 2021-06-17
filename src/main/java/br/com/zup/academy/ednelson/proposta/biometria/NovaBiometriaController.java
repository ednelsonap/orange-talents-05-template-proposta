package br.com.zup.academy.ednelson.proposta.biometria;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;
import br.com.zup.academy.ednelson.proposta.cartao.CartaoRepository;

@RestController
public class NovaBiometriaController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private BiometriaRepository biometriaRepository;

	@PostMapping("/api/propostas/cartao/{id}/biometria")
	private ResponseEntity<?> cadastrar(@PathVariable("id") String uuidCartao,
			@RequestBody @Valid NovaBiometriaRequest request, UriComponentsBuilder uriBuilder) {

		Optional<Cartao> possivelCartao = cartaoRepository.findByUuid(uuidCartao);
		if (possivelCartao.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Cartao cartao = possivelCartao.get();
		Biometria biometria = request.toModel(cartao);
		biometriaRepository.save(biometria);

		URI uri = uriBuilder.path("/api/propostas/cartao/{id_cartao}/biometria/{id}").buildAndExpand(cartao.getUuid(), biometria.getUuid()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
