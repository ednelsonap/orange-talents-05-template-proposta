package br.com.zup.academy.ednelson.proposta.feign;

public class CarteiraFeignResponse {

	private String resultado;
	private String id;
	
	public CarteiraFeignResponse(String resultado, String id) {
		this.resultado = resultado;
		this.id = id;
	}

	public String getResultado() {
		return resultado;
	}
	
	public String getId() {
		return id;
	}
	
}
