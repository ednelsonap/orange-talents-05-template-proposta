package br.com.zup.academy.ednelson.proposta.cartao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class AvisoViagem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	private String destino;
	private LocalDate validoAte;
	private LocalDateTime instanteDoAviso = LocalDateTime.now();
	private String ipCliente;
	private String userAgentCliente;
	@OneToOne
	private Cartao cartao;
	
	@Deprecated
	public AvisoViagem() {

	}
	
	public AvisoViagem(String destino, LocalDate validoAte, String ipCliente,
			String userAgentCliente, Cartao cartao) {
		this.destino = destino;
		this.validoAte = validoAte;
		this.ipCliente = ipCliente;
		this.userAgentCliente = userAgentCliente;
		this.cartao = cartao;
	}

	public String getUuid() {
		return uuid;
	}

}
