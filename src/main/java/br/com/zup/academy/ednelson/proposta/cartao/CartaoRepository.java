package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	Optional<Cartao> findByNumero(String idCartao);

	Optional<Cartao> findByUuid(String idCartao);
	
	Optional<List<Cartao>> findByStatus(StatusCartao bloqueioPendente);

}
