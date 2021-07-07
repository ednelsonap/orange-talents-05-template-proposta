package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.academy.ednelson.proposta.feign.SolicitacaoAnaliseResourceClient;
import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
public class NovaPropostaController {

	@Autowired
	private PropostaRepository propostaRepository;
	@Autowired
	private SolicitacaoAnaliseResourceClient solicitacaoAnaliseResourceClient;
	@Autowired
	private PropostasMetrics propostasMetrics;
	private final Logger logger = LoggerFactory.getLogger(NovaPropostaController.class);
	private final Tracer tracer;
	
	public NovaPropostaController(Tracer tracer) {
		this.tracer = tracer;
	}
	
	@PostMapping("/api/propostas")
	@Transactional
	public ResponseEntity<?> criar(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {

		Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(request.getDocumento());

		if (possivelProposta.isPresent()) {
			return ResponseEntity.unprocessableEntity().body("Já existe uma proposta para este solicitante");
		}
		
		Proposta proposta = request.toModel();
		propostaRepository.save(proposta);
		proposta.verificaRestricaoFinanceira(solicitacaoAnaliseResourceClient);
		
		Span activeSpan = tracer.activeSpan();
		activeSpan.setTag("proposta.email", proposta.getEmail());
		
		logger.info(String.format("Proposta criada com sucesso para: {nome: %s, documento: %s}",
				proposta.getNome(), proposta.getDocumento()));
		
		propostasMetrics.contaPropostasCriadas();

		URI uri = uriBuilder.path("/api/propostas/{id}").buildAndExpand(proposta.getUuid()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
