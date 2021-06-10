package br.com.zup.academy.ednelson.proposta.novaproposta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

	Optional<Proposta> findByDocumento(String documento);

	@Query("FROM Proposta p WHERE p.estado = :pEstado AND p.cartao IS NULL")
	List<Proposta> findByEstadoAndCartaoIsNull(@Param("pEstado") Estado elegivel);
	
}
