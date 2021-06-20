package br.com.zup.academy.ednelson.proposta.novaproposta;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;


@Component
public class PropostasMetrics {

	private final MeterRegistry meterRegistry;

	public PropostasMetrics(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	public void contaPropostasCriadas() {
		contador("proposta_criada");
	}

	private void contador(String nomeDaMetrica) {
		Counter counter = this.meterRegistry.counter(nomeDaMetrica);
		counter.increment();
	}
}
