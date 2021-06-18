package br.com.zup.academy.ednelson.proposta.cartao;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Bloqueio {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	@OneToOne
	@NotNull
	private Cartao cartao;
	@NotBlank
	private String ipDoClienteQueBloqueou;
	@NotBlank
	private String userAgentDoClienteQueBloqueou;
	private LocalDateTime bloqueadoEm = LocalDateTime.now();
	
	@Deprecated
	public Bloqueio() {
		
	}
	
	public Bloqueio(@NotNull Cartao cartao, @NotBlank String ipDoClienteQueBloqueou,
			@NotBlank String userAgentDoClienteQueBloqueou) {
		this.cartao = cartao;
		this.ipDoClienteQueBloqueou = ipDoClienteQueBloqueou;
		this.userAgentDoClienteQueBloqueou = userAgentDoClienteQueBloqueou;
	}
	
	public String getUuid() {
		return uuid;
	}
}
