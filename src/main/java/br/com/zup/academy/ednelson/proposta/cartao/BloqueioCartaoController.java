package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BloqueioCartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private RealizaBloqueioDeCartao realizaBloqueioDeCartao;
	
	@PostMapping("/api/propostas/cartoes/{id}/bloqueios")
	private ResponseEntity<?> bloquear(@PathVariable("id") String uuidCartao, HttpServletRequest req) {
		
		Optional<Cartao> possivelCartao = cartaoRepository.findByUuid(uuidCartao);
		
		if(possivelCartao.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Cartao cartao = possivelCartao.get();
		
		if(cartao.bloqueado()) {
			return ResponseEntity.unprocessableEntity().body("Este cartão já está bloqueado");
		}
		
		realizaBloqueioDeCartao.bloquear(cartao, req.getRemoteAddr(), req.getHeader("User-Agent"));
	
		return ResponseEntity.ok().build();
		
	}
}
