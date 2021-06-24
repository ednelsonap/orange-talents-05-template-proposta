package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class BloqueioCartaoController {

	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private RealizaBloqueioDeCartao realizaBloqueioDeCartao;
	@Autowired
	private Tracer tracer;
	private final Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);
	
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
	
		logger.info(String.format("Solicitação de bloqueio de cartão efetuada para: {titular: %s, uuidCartao: %s}",
				cartao.getTitular(), cartao.getUuid()));
		
		Span activeSpan = tracer.activeSpan();
		activeSpan.setTag("cartao.titular", cartao.getTitular());
		
		return ResponseEntity.ok().build();
		
	}
}
