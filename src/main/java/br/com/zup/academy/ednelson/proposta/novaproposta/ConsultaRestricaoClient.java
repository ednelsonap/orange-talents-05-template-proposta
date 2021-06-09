package br.com.zup.academy.ednelson.proposta.novaproposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${zup.analise.financeira}", name = "consultaRestricao")
public interface ConsultaRestricaoClient {

	@RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
	ResultadoAnaliseDto consultar(SolicitacaoAnaliseRequest request);
}
