package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	@Query("select c from ContaPagar c where upper(trim(c.descricao)) like %?1%")
	List<ContaPagar> buscaContaDesc(String desc);
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from conta_pagar where upper(trim(descricao)) like %?1%")
	boolean existeContaPagarComDescricao(String desc);
	
	@Query("select c from ContaPagar c where upper(trim(c.descricao)) like %?1% and c.empresa.id = ?2")
	List<ContaPagar> buscaContaDesc(String desc, Long IdEmpresa);
	
	@Query("select c from ContaPagar c where c.pessoa.id = ?1")
	List<ContaPagar> buscaPorPessoa(Long idPessoa);
	
	@Query("select c from ContaPagar c where c.pessoa_fornecedor.id = ?1")
	List<ContaPagar> buscaPorFornecedor(Long idFornecedor);
	
	@Query("select c from ContaPagar c where c.empresa.id = ?1")
	List<ContaPagar> buscaPorEmpresa(Long idEmpresa);
	
	

}
