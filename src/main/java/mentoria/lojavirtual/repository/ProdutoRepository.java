package mentoria.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mentoria.lojavirtual.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM produto WHERE upper(trim(nome)) = upper(trim(?1));")
	public boolean existeProduto(String nomeProduto);

	@Query("select p from Produto p where upper(p.nome) like %?1% ")
	public List<Produto> buscarProdutoNome(String nome);
	
	@Query("select p from Produto p where upper(p.nome) like %?1% and p.empresa.id = ?2")
	public List<Produto> buscarProdutoNome(String nome, Long idEmpresa);
}
