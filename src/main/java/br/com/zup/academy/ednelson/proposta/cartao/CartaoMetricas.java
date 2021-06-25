package br.com.zup.academy.ednelson.proposta.cartao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class CartaoMetricas {
	
	@Autowired
	private MeterRegistry meterRegistry;

	public void contaCartoesEmitidos() {
		contador("cartoes_emitidos_total");
	}

	private void contador(String nomeDaMetrica) {
		Counter counter = this.meterRegistry.counter(nomeDaMetrica);
		counter.increment();
	}
	
}
