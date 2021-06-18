package br.com.zup.academy.ednelson.proposta.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloqueioCartaoResponse {

	@JsonProperty
	private String resultado;

	public Bloqueio toModel(Cartao cartao, String ipDoCliente, String UserAgentDoCliente) {
		return new Bloqueio(cartao, ipDoCliente, UserAgentDoCliente);
	}

	public String getResultado() {
		return resultado;
	}
}
