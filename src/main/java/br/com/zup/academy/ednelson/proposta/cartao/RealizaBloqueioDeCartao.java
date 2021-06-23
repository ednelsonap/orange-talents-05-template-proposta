package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.academy.ednelson.proposta.feign.CartaoClient;
import feign.FeignException;

@Component
public class RealizaBloqueioDeCartao {

	@Autowired
	private BloqueioRepository bloqueioRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private CartaoClient cartaoResourceClient;

	public void bloquear(Cartao cartao, String idDoClienteQueBloqueou, String userAgentDoClienteQueBloqueou) {
		Bloqueio bloqueio = new Bloqueio(cartao, idDoClienteQueBloqueou, userAgentDoClienteQueBloqueou); 
		bloqueioRepository.save(bloqueio);
		cartao.alterarStatusParaBloqueioPendente();
		cartaoRepository.save(cartao);
	}
	
	@Scheduled(fixedDelayString = "${delay.scheduled.notifica.bloqueio}")
	public void notificaBloqueio() {
		Optional<List<Cartao>> cartoesComBloqueioPendente = cartaoRepository.findByStatus(StatusCartao.BLOQUEIO_PENDENTE); 
		
		if (cartoesComBloqueioPendente.isPresent()) {
			cartoesComBloqueioPendente.get().forEach(cartao -> {
				try {
					BloqueioCartaoResponse response = cartaoResourceClient.bloquear(cartao.getNumero(), new BloqueioCartaoRequest("API de Propostas"));
					if(response.getResultado().equals("BLOQUEADO")){
						cartao.bloquear();
						cartaoRepository.save(cartao);
					}
					
				} catch (FeignException e){
					
				}
			});
		}

	}
}
