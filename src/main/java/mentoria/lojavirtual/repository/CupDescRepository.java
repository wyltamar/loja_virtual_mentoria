package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.CupDesc;

@Repository
@Transactional
public interface CupDescRepository extends JpaRepository<CupDesc, Long> {
	
	
	@Query(value = "select cp from CupDesc cp where cp.empresa.id = ?1")
	public List<CupDesc> buscarCuponDescontoPorEmpresa(Long idEmpresa);
	
	@Query(value = "select cpd from CupDesc cpd where upper(trim(cpd.codDesc)) like %?1% and cpd.empresa.id = ?2")
	public List<CupDesc> buscarCupnDescontoPorCodDescricaoPorEmpresa(String codDesc, Long idEmpresa);
	
	@Query(value = "select cpd from CupDesc cpd where upper(trim(cpd.codDesc)) like %?1%")
	public List<CupDesc> buscarCupnDescontoPorCodDescricao(String codDesc);
	
}
