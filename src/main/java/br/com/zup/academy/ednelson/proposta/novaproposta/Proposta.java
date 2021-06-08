package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.academy.ednelson.proposta.validation.CpfOrCnpj;

@Entity
public class Proposta {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@CpfOrCnpj
	@Column(unique = true)
	private String documento;
	@NotBlank
	private String endereco;
	@NotNull
	@Positive
	private BigDecimal salarioBruto;
	
	@Deprecated
	public Proposta() {
		
	}
	
	public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String documento,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salarioBruto) {
		super();
		this.nome = nome;
		this.email = email;
		this.documento = documento;
		this.endereco = endereco;
		this.salarioBruto = salarioBruto;
	}

	public String getUuid() {
		return uuid;
	}
}
