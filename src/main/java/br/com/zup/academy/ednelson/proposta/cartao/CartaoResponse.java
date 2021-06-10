package br.com.zup.academy.ednelson.proposta.cartao;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoResponse {

	@JsonProperty
	private String id;
	@JsonProperty
	private String titular;
	@JsonProperty
	private BigDecimal limite;
	
	public Cartao toModel() {
		return new Cartao(id, titular, limite);
	}
}
