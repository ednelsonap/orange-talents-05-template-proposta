package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.zup.academy.ednelson.proposta.feign.CartaoClient;
import br.com.zup.academy.ednelson.proposta.novaproposta.Estado;
import br.com.zup.academy.ednelson.proposta.novaproposta.Proposta;
import br.com.zup.academy.ednelson.proposta.novaproposta.PropostaRepository;
import feign.FeignException;

@Component
public class AssociaCartaoAProposta {

	private final Logger logger = LoggerFactory.getLogger(AssociaCartaoAProposta.class);

	@Autowired
	private CartaoClient cartaoResourceClient;
	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private CartaoRepository cartaoRepository;
	@Autowired
	private CartaoMetricas cartaoMetricas;

	@Scheduled(fixedDelayString = "${delay.scheduled.cartao}")
	public void associar() {

		List<Proposta> propostas = propostaRepository.findByEstadoAndCartaoIsNull(Estado.ELEGIVEL);
		propostas.forEach(proposta -> {
			
			try {
				
				CartaoResourceResponse cartaoResponse = cartaoResourceClient
						.gerarCartaoParaAProposta(proposta.getUuid());
				Cartao cartao = cartaoResponse.toModel();
				cartaoRepository.save(cartao);
				proposta.setCartao(cartao);
				propostaRepository.save(proposta);
				
				logger.info(String.format("Cartão gerado para: {nome: %s, uuidProposta: %s}", proposta.getNome(),
						proposta.getUuid()));
				
				cartaoMetricas.contaCartoesEmitidos();
				
			} catch (FeignException e) {
				
				logger.info(String.format("Não foi possível gerar um cartão para: {nome: %s, uuidProposta: %s}",
						proposta.getNome(), proposta.getUuid()));
				
			}
		});

	}
}
