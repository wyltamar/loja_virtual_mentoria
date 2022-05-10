package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.CategoriaProduto;

@Repository
@Transactional
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long>{

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM categoria_produto WHERE upper(trim(nome_desc)) = upper(trim(?1));")
	public boolean existeCategoria(String categoria);

	@Query("select cp from CategoriaProduto cp where upper(cp.nomeDesc) like %?1% ")
	public List<CategoriaProduto> buscarCategoriaProdutoDesc(String nomeDesc);
}
