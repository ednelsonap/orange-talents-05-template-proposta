package br.com.zup.academy.ednelson.proposta.cartao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	Optional<Cartao> findByNumero(String idCartao);

	Optional<Cartao> findByUuid(String uuidCartao);

}
