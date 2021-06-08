package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PropostaRepository extends JpaRepository<Proposta, Long>{

	  Optional<Proposta> findByDocumento(String documento);

}
