package br.com.zup.academy.ednelson.proposta.cartao;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AvisoViagemRequest {
	
	@NotBlank
	private String destino;
	@FutureOrPresent
	@NotNull
	private LocalDate validoAte;

	@JsonCreator
	public AvisoViagemRequest(@NotBlank String destino, @FutureOrPresent @NotNull LocalDate validoAte) {
		this.destino = destino;
		this.validoAte = validoAte;
	}  

	public String getDestino() {
		return destino;
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}

	public AvisoViagem toModel(Cartao cartao, String ipCliente, String userAgentCliente) {
		return new AvisoViagem(destino, validoAte, ipCliente, userAgentCliente, cartao);
	}
	
}
