package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
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

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;
import br.com.zup.academy.ednelson.proposta.feign.SolicitacaoAnaliseResourceClient;
import br.com.zup.academy.ednelson.proposta.validation.CpfOrCnpj;
import feign.FeignException;

@Entity
public class Proposta {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid = UUID.randomUUID().toString();
	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@CpfOrCnpj
	@Column(unique = true)
	private String documento;
	@NotBlank
	private String endereco;
	@NotNull
	@Positive
	private BigDecimal salarioBruto;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	@OneToOne
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

	public void verificaRestricaoFinanceira(SolicitacaoAnaliseResourceClient solicitacaoAnaliseResourceClient) throws JsonMappingException, JsonProcessingException {		
		try {
			SolicitacaoAnaliseRequest solicitacao = new SolicitacaoAnaliseRequest(documento, nome, this.uuid);
			ResultadoAnaliseDto resultado = solicitacaoAnaliseResourceClient.consultar(solicitacao);		
			if(resultado.getResultadoSolicitacao().equals("SEM_RESTRICAO")) {
				this.estado = Estado.ELEGIVEL;
			}	
		} catch (FeignException e) {
			ResultadoAnaliseDto resultado = new ObjectMapper().readValue(e.contentUTF8(),
					ResultadoAnaliseDto.class);
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()
                    && resultado.getResultadoSolicitacao().equals("COM_RESTRICAO")) {
                this.estado = Estado.NAO_ELEGIVEL;
            }
		}
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	
}
