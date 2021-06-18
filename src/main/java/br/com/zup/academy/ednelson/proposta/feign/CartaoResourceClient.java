package br.com.zup.academy.ednelson.proposta.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.academy.ednelson.proposta.cartao.BloqueioCartaoRequest;
import br.com.zup.academy.ednelson.proposta.cartao.BloqueioCartaoResponse;
import br.com.zup.academy.ednelson.proposta.cartao.CartaoResourceResponse;

@FeignClient(url = "${zup.cartaoresource}/api/cartoes", name = "cartaoResource")
public interface CartaoResourceClient {

	@RequestMapping(method = RequestMethod.GET, path = "?idProposta={idProposta}")
	CartaoResourceResponse gerarCartaoParaAProposta(@PathVariable("idProposta") String idProposta);

	@RequestMapping(method = RequestMethod.POST, path = "/{id}/bloqueios")
	BloqueioCartaoResponse bloquear(@PathVariable("id") String idCartao, BloqueioCartaoRequest request);
	
}
