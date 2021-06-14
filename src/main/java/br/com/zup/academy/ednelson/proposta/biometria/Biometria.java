package br.com.zup.academy.ednelson.proposta.biometria;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;

@Entity
public class Biometria {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	@NotBlank
	@Lob
	private String fingerPrint;
	private LocalDateTime localDateTime = LocalDateTime.now();
	@ManyToOne
	@NotNull
	private Cartao cartao;
	
	@Deprecated
	public Biometria() {
	
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public Biometria(@NotBlank String fingerPrint, @NotNull Cartao cartao) {
		this.fingerPrint = fingerPrint;
		this.cartao = cartao;
	}
	
}
