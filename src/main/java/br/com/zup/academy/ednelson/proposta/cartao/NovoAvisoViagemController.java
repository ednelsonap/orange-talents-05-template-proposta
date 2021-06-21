package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.academy.ednelson.proposta.feign.AvisoViagemFeignResponse;
import br.com.zup.academy.ednelson.proposta.feign.CartaoResourceClient;
import feign.FeignException;

@RestController
public class NovoAvisoViagemController {

	@Autowired
	private AvisoViagemRepository avisoViagemRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private CartaoResourceClient cartaoResourceClient;

	@PostMapping("/api/propostas/cartao/{uuid}/avisos")
	private ResponseEntity<?> cadastrar(@PathVariable("uuid") UUID id, @RequestBody @Valid AvisoViagemRequest request,
			HttpServletRequest req) {

		Optional<Cartao> cartao = cartaoRepository.findByUuid(id.toString());

		if (cartao.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		try {
			
			AvisoViagemFeignResponse response = cartaoResourceClient.notificaAvisoViagem(cartao.get().getNumero(), request);
			
			if (response.getResultado().equals("CRIADO")) {
				AvisoViagem avisoViagem = request.toModel(cartao.get(), req.getRemoteAddr(),
						req.getHeader("User-Agent"));
				avisoViagemRepository.save(avisoViagem);
			}
			
			return ResponseEntity.ok().build();
			
		} catch (FeignException e) {
			
			return ResponseEntity.status(e.status()).build();
			
		}
	}
}
