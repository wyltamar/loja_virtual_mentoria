package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.StatusRastreio;

@Repository
@Transactional
public interface StatusRastreioRepository extends JpaRepository<StatusRastreio, Long> {
	
	@Query(value = "select s from StatusRastreio s where s.vendaCompraLojaVirtual.id = ?1 order by s.id")
	public List<StatusRastreio> listaRastreioVenda(Long idVenda);

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "update vd_cp_loja_virt set url_rastreio = ?1 where id = ?2")
	public void salvarUrlRastreio(String urlRastreio, Long idVenda);
	
	

}
