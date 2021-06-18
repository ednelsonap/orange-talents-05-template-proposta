package br.com.zup.academy.ednelson.proposta.cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RealizaBloqueioDeCartao {

	@Autowired
	private BloqueioRepository bloqueioRepository;
	@Autowired
	private CartaoRepository cartaoRepository;

	public void bloquear(Cartao cartao, String idDoClienteQueBloqueou, String userAgentDoClienteQueBloqueou) {
		Bloqueio bloqueio = new Bloqueio(cartao, idDoClienteQueBloqueou, userAgentDoClienteQueBloqueou); 
		bloqueioRepository.save(bloqueio);
		cartao.bloquear();
		cartaoRepository.save(cartao);
	}
	
}
