package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zup.academy.ednelson.proposta.cartao.CartaoResponse;

public class PropostaResponse {

	@JsonProperty
	private String uuid = UUID.randomUUID().toString();
	@JsonProperty
	private String nome;
	@JsonProperty
	private String email;
	@JsonProperty
	private String documento;
	@JsonProperty
	private String endereco;
	@JsonProperty
	private BigDecimal salarioBruto;
	@JsonProperty
	private String estado;
	@JsonProperty
	private CartaoResponse cartao;
	
	public PropostaResponse(Proposta proposta) {
		this.uuid = proposta.getUuid();
		this.nome = proposta.getNome();
		this.email = proposta.getEmail();
		this.documento = proposta.getDocumento();
		this.endereco = proposta.getEndereco();
		this.salarioBruto = proposta.getSalarioBruto();
		this.estado = proposta.getEstado().toString();
		this.cartao = new CartaoResponse(proposta.getCartao());
	}
}
