package br.com.zup.academy.ednelson.proposta.paypal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;

@Entity
public class Carteira {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String email;
	private String emissor;
	@ManyToOne
	private Cartao cartao;
	
	@Deprecated
	public Carteira() {
		
	}
	
	public Carteira(String uuid, String email, String emissor, Cartao cartao) {
		this.uuid = uuid;
		this.email = email;
		this.emissor = emissor;
		this.cartao = cartao;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getEmissor() {
		return emissor;
	}
}
