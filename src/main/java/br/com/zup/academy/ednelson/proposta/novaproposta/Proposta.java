package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;
import br.com.zup.academy.ednelson.proposta.feign.SolicitacaoAnaliseResourceClient;
import br.com.zup.academy.ednelson.proposta.security.Criptografador;
import feign.FeignException.FeignClientException;

@Entity
public class Proposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Convert(converter = Criptografador.class)
	@Column(unique = true)
	private String documento;
	@NotBlank
	private String endereco;
	@NotNull
	@Positive
	private BigDecimal salarioBruto;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	@OneToOne(cascade = CascadeType.REMOVE)
	private Cartao cartao;

	@Deprecated
	public Proposta() {

	}

	public Proposta(@NotBlank String nome, @NotBlank @Email String email, @NotBlank String documento,
			@NotBlank String endereco, @NotNull @Positive BigDecimal salarioBruto) {
		super();
		this.nome = nome;
		this.email = email;
		this.documento = documento;
		this.endereco = endereco;
		this.salarioBruto = salarioBruto;
	}

	public String getUuid() {
		return uuid;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getDocumento() {
		return documento;
	}

	public String getEndereco() {
		return endereco;
	}

	public BigDecimal getSalarioBruto() {
		return salarioBruto;
	}

	public Estado getEstado() {
		return estado;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void verificaRestricaoFinanceira(SolicitacaoAnaliseResourceClient solicitacaoAnaliseResourceClient) {
		try {
			SolicitacaoAnaliseRequest solicitacao = new SolicitacaoAnaliseRequest(documento, nome, this.uuid);
			ResultadoAnaliseDto resultado = solicitacaoAnaliseResourceClient.consultar(solicitacao);
			if (resultado.getResultadoSolicitacao().equals("SEM_RESTRICAO")) {
				this.estado = Estado.ELEGIVEL;
			}
		} catch (FeignClientException.UnprocessableEntity e) {
			this.estado = Estado.NAO_ELEGIVEL;
		}
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

}
