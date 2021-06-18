package br.com.zup.academy.ednelson.proposta.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioCartaoRequest {

	@JsonProperty
	private String sistemaResponsavel;

	public BloqueioCartaoRequest(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

}
