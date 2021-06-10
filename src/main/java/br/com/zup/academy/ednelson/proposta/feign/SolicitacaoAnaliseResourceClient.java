package br.com.zup.academy.ednelson.proposta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.academy.ednelson.proposta.novaproposta.ResultadoAnaliseDto;
import br.com.zup.academy.ednelson.proposta.novaproposta.SolicitacaoAnaliseRequest;

@FeignClient(url = "${zup.analisefinanceira}/api", name = "analiseFinanceira")
public interface SolicitacaoAnaliseResourceClient {

	@RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
	ResultadoAnaliseDto consultar(SolicitacaoAnaliseRequest request);
}
