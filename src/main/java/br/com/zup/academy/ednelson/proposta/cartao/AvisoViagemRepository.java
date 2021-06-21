package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AvisoViagemRepository extends JpaRepository<AvisoViagem, Long>{

	Optional<AvisoViagem> findByUuid(String idCartao);
	
}
