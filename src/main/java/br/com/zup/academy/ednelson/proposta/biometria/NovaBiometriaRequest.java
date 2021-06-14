package br.com.zup.academy.ednelson.proposta.biometria;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;
import br.com.zup.academy.ednelson.proposta.validation.IsBase64;

public class NovaBiometriaRequest {

	@JsonProperty
	@IsBase64
	@Lob
	@NotBlank
	private String fingerPrint;

	public String getFingerPrint() {
		return fingerPrint;
	}
	
	public Biometria toModel(Cartao cartao) {
		return new Biometria(fingerPrint, cartao);
	}
}
