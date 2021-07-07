package br.com.zup.academy.ednelson.proposta.paypal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private Emissor emissor;
	@ManyToOne
	private Cartao cartao;
	
	@Deprecated
	public Carteira() {
		
	}
	
	public Carteira(String uuid, String email, String emissor, Cartao cartao) {
		this.uuid = uuid;
		this.email = email;
		this.emissor = Emissor.valueOf(emissor);
		this.cartao = cartao;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((emissor == null) ? 0 : emissor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carteira other = (Carteira) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (emissor != other.emissor)
			return false;
		return true;
	}
	
	
}
