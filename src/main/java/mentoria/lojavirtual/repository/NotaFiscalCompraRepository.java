package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.NotaFiscalCompra;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {
	
	
	@Query("select a from NotaFiscalCompra a where upper(trim(a.descricaoObs)) like %?1%")
	public List<NotaFiscalCompra> buscarNotaPorDesc(String desc);
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from nota_fiscal_compra where upper(descricao_obs) like %?1%")
	public boolean existeNotaComDescricao(String desc);
	
	@Query("select a from NotaFiscalCompra a where a.pessoa.id = ?1")
	public List<NotaFiscalCompra> buscarNotaPor(Long idPessoa);
	
	@Query("select a from NotaFiscalCompra a where a.contaPagar.id = ?1")
	public List<NotaFiscalCompra> buscarNotaPorContaPagar(Long idContaPagar);
	
	@Query("select a from NotaFiscalCompra a where a.empresa.id = ?1")
	public List<NotaFiscalCompra> buscarNotaPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "delete from nota_item_produto where nota_fiscal_copra_id = ?1")
	public void deleteItemNotaFiscal(Long idNotaFiscalCompra);

}
