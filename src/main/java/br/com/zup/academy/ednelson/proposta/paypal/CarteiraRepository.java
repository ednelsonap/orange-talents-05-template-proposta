package br.com.zup.academy.ednelson.proposta.paypal;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.academy.ednelson.proposta.cartao.Cartao;

public interface CarteiraRepository extends JpaRepository<Carteira, Long>{

	Optional<Carteira> findByCartaoAndEmissor(Long id, String carteira);

	Optional<Carteira> findByCartaoAndEmissor(Cartao cartao, String carteira);

}
