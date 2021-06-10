package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.academy.ednelson.proposta.feign.CartaoResourceClient;
import br.com.zup.academy.ednelson.proposta.novaproposta.Estado;
import br.com.zup.academy.ednelson.proposta.novaproposta.Proposta;
import br.com.zup.academy.ednelson.proposta.novaproposta.PropostaRepository;

@Component
public class AssociaCartaoAProposta {

	@Autowired
	private CartaoResourceClient cartaoResourceClient;
	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private CartaoRepository cartaoRepository;

	@Scheduled(fixedDelay = 10000)
	public void associar() {
		List<Proposta> propostas = propostaRepository.findByEstadoAndCartaoIsNull(Estado.ELEGIVEL);

		propostas.forEach(proposta -> {
			CartaoResponse cartaoResponse = cartaoResourceClient.gerarCartaoParaAProposta(proposta.getUuid());
			Cartao cartao = cartaoResponse.toModel();
			cartaoRepository.save(cartao);
			proposta.setCartao(cartao);
			propostaRepository.save(proposta);
		});
	}
}
