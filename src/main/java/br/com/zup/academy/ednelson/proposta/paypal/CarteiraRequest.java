package br.com.zup.academy.ednelson.proposta.paypal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;

public class CarteiraRequest {

	@Email
	@NotBlank
	private String email;
	
	private String carteira;

	public CarteiraRequest(@Email @NotBlank String email, @NotBlank String carteira) {
		this.email = email;
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}

	public Carteira toModel(Cartao cartao, String uuid) {
		return new Carteira(uuid, email, carteira, cartao);
	}

}
