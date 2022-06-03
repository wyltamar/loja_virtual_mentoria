package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.NotaItemProduto;

@Repository
@Transactional
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {
	
	@Query("select a from NotaItemProduto a where a.produto.id = ?1 and a.notaFiscalCompra.id = ?2  ")
	public List<NotaItemProduto> buscaNotaItemPorProdutoNota(Long idProduto, Long idNotaFiscal);
	
	@Query("select a from NotaItemProduto a where a.produto.id = ?1")
	public List<NotaItemProduto> buscaNotaItemPorProduto(Long idProduto);
	
	@Query("select a from NotaItemProduto a where a.notaFiscalCompra.id = ?1")
	public List<NotaItemProduto> buscaNotaItemPorNotaFiscal(Long idNotaFiscal);
	
	@Query("select a from NotaItemProduto a  where a.empresa.id = ?1")
	public List<NotaItemProduto> buscaNotaItemPorEmpresa(Long idEmpresa);

}
