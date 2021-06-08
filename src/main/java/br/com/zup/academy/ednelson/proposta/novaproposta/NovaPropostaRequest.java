package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.academy.ednelson.proposta.validation.CpfOrCnpj;

public class NovaPropostaRequest {
	
	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@CpfOrCnpj
	private String documento;
	@NotBlank
	private String endereco;
	@NotNull
	@Positive
	private BigDecimal salarioBruto;
	
	public NovaPropostaRequest(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String documento,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salarioBruto) {
		this.nome = nome;
		this.email = email;
		this.documento = documento;
		this.endereco = endereco;
		this.salarioBruto = salarioBruto;
	}

	public String getDocumento() {
		return documento;
	}
	
	public Proposta toModel() {
		return new Proposta(nome, email, documento, endereco, salarioBruto);
	}
	
}
