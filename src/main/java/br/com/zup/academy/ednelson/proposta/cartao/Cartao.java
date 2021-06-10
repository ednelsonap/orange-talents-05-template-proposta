package br.com.zup.academy.ednelson.proposta.cartao;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Cartao {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numero;
	@NotBlank
	private String titular;
	@NotNull
	private BigDecimal limite;
	
	@Deprecated
	public Cartao() {
		
	}
	
	public Cartao(String numero, String titular, BigDecimal limite) {
		this.numero = numero;
		this.titular = titular;
		this.limite = limite;
	}
	
}
