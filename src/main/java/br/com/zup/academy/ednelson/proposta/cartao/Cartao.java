package br.com.zup.academy.ednelson.proposta.cartao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.academy.ednelson.proposta.biometria.Biometria;
import br.com.zup.academy.ednelson.proposta.paypal.Carteira;

@Entity
public class Cartao {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	@NotBlank
	private String numero;
	@NotBlank
	private String titular;
	@NotNull
	private BigDecimal limite;
	@OneToMany(mappedBy = "cartao")
	private List<Biometria> biometrias = new ArrayList<>();
	@Enumerated(EnumType.STRING)
	private StatusCartao status = StatusCartao.ATIVO;
	@OneToMany(mappedBy = "cartao")
	private Set<Carteira> carteiras = new HashSet<Carteira>();
	
	@Deprecated
	public Cartao() {
		
	}
	
	public Cartao(String numero, String titular, BigDecimal limite) {
		this.numero = numero;
		this.titular = titular;
		this.limite = limite;
	}

	public Long getId() {
		return id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public String getNumero() {
		return numero;
	}

	public String getTitular() {
		return titular;
	}

	public BigDecimal getLimite() {
		return limite;
	}
	
	public boolean bloqueado(){
        return status.equals(StatusCartao.BLOQUEADO);
    }
	
	public void alterarStatusParaBloqueioPendente() {
		this.status = StatusCartao.BLOQUEIO_PENDENTE;
	}
	
	public void bloquear() {
		this.status = StatusCartao.BLOQUEADO;
	}
}
