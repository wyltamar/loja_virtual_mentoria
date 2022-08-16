package mentoria.lojavirtual.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import mentoria.lojavirtual.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

	@Query(value = "select fp from FormaPagamento fp where fp.empresa.id = ?1 ")
	public List<FormaPagamento> findAll(Long idEmpresa);
	

}
