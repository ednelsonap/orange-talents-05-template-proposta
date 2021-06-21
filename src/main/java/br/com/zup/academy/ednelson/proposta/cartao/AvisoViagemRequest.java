package br.com.zup.academy.ednelson.proposta.cartao;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AvisoViagemRequest {
	
	@NotBlank
	private String destinoDaViagem;
	@FutureOrPresent
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dataDeTerminoDaViagem;

	@JsonCreator
	public AvisoViagemRequest(@NotBlank String destinoDaViagem,
			@FutureOrPresent @NotNull LocalDate dataDeTerminoDaViagem) {
		super();
		this.destinoDaViagem = destinoDaViagem;
		this.dataDeTerminoDaViagem = dataDeTerminoDaViagem;
	}

	public AvisoViagem toModel(Cartao cartao, String ipCliente, String userAgentCliente) {
		return new AvisoViagem(destinoDaViagem, dataDeTerminoDaViagem, ipCliente, userAgentCliente, cartao);
	}
	
}
