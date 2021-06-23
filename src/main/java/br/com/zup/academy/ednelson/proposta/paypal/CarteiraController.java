package br.com.zup.academy.ednelson.proposta.paypal;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;
import br.com.zup.academy.ednelson.proposta.cartao.CartaoRepository;
import br.com.zup.academy.ednelson.proposta.feign.CartaoClient;
import br.com.zup.academy.ednelson.proposta.feign.CarteiraFeignResponse;
import br.com.zup.academy.ednelson.proposta.validation.EmissorValidator;
import feign.FeignException;

@RestController
public class CarteiraController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private CartaoClient cartaoClient;
	@Autowired
	private CarteiraRepository carteiraRepository;
	@Autowired
	private EmissorValidator emissorValidator;
	
	@InitBinder
	public void init(WebDataBinder binder) {
		binder.addValidators(emissorValidator);
	}
	
	@PostMapping("/api/propostas/cartao/{uuid}/carteiras")
	public ResponseEntity<?> criar(@PathVariable("uuid") UUID idCartao, @RequestBody @Valid CarteiraRequest request, 
			UriComponentsBuilder uriBuilder) {

		Optional<Cartao> cartao = cartaoRepository.findByUuid(idCartao.toString());

		if (cartao.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Optional<Carteira> possivelCarteira = carteiraRepository.findByCartaoAndEmissor(cartao.get(), Emissor.valueOf(request.getCarteira()));
		 
		if(possivelCarteira.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("Cartão já associado a esta carteira");
		}
		
		try {

			CarteiraFeignResponse response = cartaoClient.associaCartaoAoPaypal(cartao.get().getNumero(), request);

			Carteira carteira = request.toModel(cartao.get(), response.getId());
			carteiraRepository.save(carteira);

			URI uri = uriBuilder.path("/api/propostas/cartao/{uuid_cartao}/carteiras/{id}")
					.buildAndExpand(cartao.get().getUuid(), carteira.getUuid()).toUri();
			
			return ResponseEntity.created(uri).build();

		} catch (FeignException e) {
			return ResponseEntity.status(e.status()).build();
		}

	}
}
